#! /bin/sh

function error_exit() {
	msg="$*"
	exec 1>&2
	echo "Error: $msg"
	exit 1
}

if test "$#" -ne 1; then
	exec 1>&2
	echo "Usage: $0 VERSION"
	exit 1
fi

version="$1"
tag="v$1"
branch="release-$tag"

echo "checking current branch"
current_branch=`git rev-parse --abbrev-ref HEAD`
test "$current_branch" = 'main' || \
	error_exit "switch to branch main first"

echo "check for uncommitted changes"
git diff --quiet || \
	error_exit "you have uncommitted changes"
git diff --cached --quiet || \
	error_exit "you have uncommitted staged changes"

echo "check for unpushed changes"
git fetch origin
if git log origin/main..HEAD | grep -q .; then
	echo "there are commits that are not pushed to origin/main."
	exit 1
fi

echo "check whether branch '$branch' already exists"
git show-ref --verify --quiet refs/heads/$branch && \
	error_exit "branch '$branch' already exists"
echo "check whether branch '$branch' already exists remotely"
exists=`git ls-remote --heads origin "$branch"`
test "$exists" = "" || error_exit "remote branch '$branch' already exists"

echo "check whether tag '$tag' already exists"
git show-ref --verify --quiet refs/tags/$tag && \
	error_exit "tag '$tag' already exists"
echo "check whether tag '$tag' already exists remotely"
git ls-remote --tags origin | grep -q "refs/tags/$tag" && \
	error_exit "tag '$tag' already exists remotely."

echo "patch the Maven manifest 'pom.xml'"
perl -npi -e "s{<project\\.version>.*?</project\\.version>}{<project.version>$version</project.version>}" pom.xml || \
	error_exit "patching 'pom.xml' failed"

echo "install dependencies"
mvn clean install || exit 1

echo "build jar files"
mvn clean package || exit 1

echo "checking for jar files"
ls -l "target/validator-$version.jar" || exit 1
ls -l "target/validator-$version-jar-with-dependencies.jar" || exit 1

set -e
echo "create branch '$branch' and switch to it"
git checkout -b "$branch"

echo "commit all changes"
git commit -a -m "bump version to $version"

echo "merge into main"
git switch main && git merge --no-edit "$branch"

echo "tag with release tag '$tag'"
git tag "$tag" || exit 1

echo "push branches"
git push origin main "$branch" || exit 1

echo "push tags"
git push --tags || exit 1

echo "delete release branch"
git branch -d "$branch"

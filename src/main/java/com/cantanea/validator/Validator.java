package com.cantanea.validator;

import io.javalin.http.UploadedFile;

import org.mustangproject.validator.ZUGFeRDValidator;

public class Validator {
	private ZUGFeRDValidator zfv;

	public Validator() {
		this.zfv = new ZUGFeRDValidator();
		this.zfv.disableNotices();
	}

	public String validate(UploadedFile file) {
		String xml = this.zfv.validate(file.content(), file.filename());

		return xml;
	}

	public boolean wasValid() {
		return this.zfv.wasCompletelyValid();
	}
}

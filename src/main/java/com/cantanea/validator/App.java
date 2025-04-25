package com.cantanea.validator;

import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

public class App {
	public static void main(String[] args) {
		int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

		Javalin app = Javalin.create(config -> {
			config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
		}).start(port);

		app.post("/validate", ctx -> {
			UploadedFile uploaded = ctx.uploadedFile("invoice");
			if (uploaded == null) {
				ctx.status(400).result("Missing file");

				return;
			}

			Validator validator = new Validator();
			String xmlResult = validator.validate(uploaded);
			int statusCode = validator.wasValid() ? 200 : 400;

			ctx.status(statusCode).contentType("application/xml").result(xmlResult);
		});
	}
}

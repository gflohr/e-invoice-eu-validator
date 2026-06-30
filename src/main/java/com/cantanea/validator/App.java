package com.cantanea.validator;

import io.javalin.Javalin;
import io.javalin.http.UploadedFile;
import io.javalin.plugin.bundled.CorsPluginConfig;

class CodeRequest {
	public String xml;
}


public class App {
	public static void main(String[] args) {
		int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8084"));

		var app = Javalin.create(config -> {
			config.bundledPlugins.enableCors(cors -> {
				cors.addRule(CorsPluginConfig.CorsRule::anyHost);
			});
		}).start(port);
		app.post("/xml", ctx -> {
			var req = ctx.bodyAsClass(CodeRequest.class);
			var xml = req.xml;
			Validator validator = new Validator();
			String xmlResult = validator.validate(xml);
			System.out.print(xmlResult);
			int statusCode = validator.wasValid() ? 200 : 400;
			ctx.status(statusCode).contentType("application/xml").result(xmlResult);
		});

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

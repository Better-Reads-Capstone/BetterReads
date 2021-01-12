package com.codeup.betterreads.services;

import com.codeup.betterreads.models.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("mailgunService")
public class MailgunService {

    @Value("${spring.mail.from}")
    public String from;

    @Value("${mg.domain}")
    public String mgDomain;

    @Value("${mg.apikey}")
    public String mgAPIKey;

    @Value("${app.env}")
    public String env;

//    public boolean testMode = env.equals("development");

    public JsonNode sendSimpleMessage(User user, String subject, String body) throws UnirestException {
    HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mgDomain + "/messages")
            .basicAuth("api", mgAPIKey)
            .field("from", String.format("Better Reads <%s>", from))
            .field("to", user.getEmail())
            .field("subject", subject)
            .field("text", body)
            .asJson();

    return request.getBody();
    }

    public JsonNode sendRegisterMessage(User user) throws UnirestException {
        final String subject = "Better Reads | Thanks for Registering!";
        final boolean testMode = env.equals("development");

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mgDomain + "/messages")
                .basicAuth("api", mgAPIKey)
                .field("from", String.format("Better Reads <%s>", from))
                .field("to", user.getEmail())
                .field("subject", subject)
                .field("template", "register-template")
                .field("h:X-Mailgun-Variables", "{\"test\": \"test\"}")
                .field("o:testmode", String.format("%s", testMode))
                .asJson();

        return request.getBody();
    }

    public JsonNode sendRegisterMessage(User user, boolean testMode) throws UnirestException {
        final String subject = "Better Reads | Thanks for Registering!";

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mgDomain + "/messages")
                .basicAuth("api", mgAPIKey)
                .field("from", String.format("Better Reads <%s>", from))
                .field("to", user.getEmail())
                .field("subject", subject)
                .field("template", "register-template")
                .field("h:X-Mailgun-Variables", "{\"test\": \"test\"}")
                .field("o:testmode", String.format("%s", testMode))
                .asJson();

        return request.getBody();
    }

    public JsonNode sendPasswordResetMessage(User user, String resetUrl) throws UnirestException {
        final String subject = "Better Reads | Password Reset";
        final boolean testMode = env.equals("development");
        final String resetVars = String.format(
                "{\"username\": \"%s\", \"reset_url\": \"%s\"}", user.getUsername(), resetUrl
        );
        System.out.println("resetUrl = " + resetUrl);
        System.out.println("resetVars = " + resetVars);

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mgDomain + "/messages")
                .basicAuth("api", mgAPIKey)
                .field("from", String.format("Better Reads <%s>", from))
                .field("to", user.getEmail())
                .field("subject", subject)
                .field("template", "pw_reset")
                .field("h:X-Mailgun-Variables", resetVars)
                .field("o:testmode", String.format("%s", testMode))
                .asJson();

        return request.getBody();
    }

    public JsonNode sendPasswordResetMessage(User user, String resetUrl, boolean testMode) throws UnirestException {
        final String subject = "Better Reads | Password Reset";
        final String resetVars = String.format(
                "{\"username\": \"%s\", \"reset_url\": \"%s\"}", user.getUsername(), resetUrl
        );
        System.out.println("resetUrl = " + resetUrl);
        System.out.println("resetVars = " + resetVars);

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mgDomain + "/messages")
                .basicAuth("api", mgAPIKey)
                .field("from", String.format("Better Reads <%s>", from))
                .field("to", user.getEmail())
                .field("subject", subject)
                .field("template", "pw_reset")
                .field("h:X-Mailgun-Variables", resetVars)
                .field("o:testmode", String.format("%s", testMode))
                .asJson();

        return request.getBody();
    }
}

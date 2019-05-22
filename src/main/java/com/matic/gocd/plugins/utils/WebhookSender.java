package com.matic.gocd.plugins.utils;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.go.plugin.api.logging.Logger;

public class WebhookSender {
    private static final Logger LOG = Logger.getLoggerFor(WebhookSender.class);

    public void send(final String endpoint, String requestBody) {
        Unirest.post(endpoint)
                .header("content-type", "application/json")
                .body(requestBody)
                .asJsonAsync(new Callback<JsonNode>() {
                                 public void failed(UnirestException e) {
                                     LOG.info("Notification has failed to: " + endpoint, e);
                                 }

                                 public void completed(HttpResponse<JsonNode> response) {
                                     int code = response.getStatus();
                                     LOG.info("Notification response " + code + ". Sent to: " + endpoint);
                                 }

                                 public void cancelled() {
                                     LOG.info("Notification was canceled to: " + endpoint);
                                 }
                             }
                );
    }
}

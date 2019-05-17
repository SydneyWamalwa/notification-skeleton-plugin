package com.matic.gocd.plugins.utils;


import com.mashape.unirest.http.Unirest;

public class WebhookSender {
    public void send(String endpoint, String requestBody) {
        Unirest.post(endpoint)
                .header("content-type", "application/json")
                .body(requestBody)
                .asJsonAsync();
    }
}

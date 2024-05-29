package com.matic.gocd.plugins.executors;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matic.gocd.plugins.RequestExecutor;
import com.thoughtworks.go.plugin.api.request.GoApiRequest;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import java.util.HashMap;
import java.util.Map;

public class StageStatusRequestExecutor implements RequestExecutor {

    private static final Gson GSON= new GsonBuilder().create();

    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request){

        Map<String, Object> responseMap= new HashMap<>();

        responseMap.put("status", "success");
        responseMap.put("message", "Stage status update processed successfully");

        String responseBody= GSON.toJson(responseMap);

        return new DefaultGoPluginApiResponse(DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE, responseBody);
    }
}

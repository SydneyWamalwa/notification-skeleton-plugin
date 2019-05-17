/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matic.gocd.plugins.executors;

import com.google.gson.Gson;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GetPluginConfigurationExecutorTest {

    @Test
    public void shouldSerializeAllFields() throws Exception {
        GoPluginApiResponse response = new GetPluginConfigurationExecutor().execute();
        HashMap hashMap = new Gson().fromJson(response.responseBody(), HashMap.class);
        assertEquals("Are you using anonymous inner classes — see https://github.com/google/gson/issues/298",
                hashMap.size(),
                0
        );
    }

    @Test
    public void assertJsonStructure() throws Exception {
        GoPluginApiResponse response = new GetPluginConfigurationExecutor().execute();

        assertThat(response.responseCode(), CoreMatchers.is(200));
        String expectedJSON = "{}";

        JSONAssert.assertEquals(expectedJSON, response.responseBody(), true);
    }
}

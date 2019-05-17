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

import com.matic.gocd.plugins.utils.Util;
import com.google.gson.Gson;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GetViewRequestExecutorTest {

    @Test
    public void shouldRenderTheTemplateInJSON() throws Exception {
        GoPluginApiResponse response = new GetViewRequestExecutor().execute();
        assertThat(response.responseCode(), is(200));
        Map<String, String> hashSet = new Gson().fromJson(response.responseBody(), HashMap.class);
        assertThat(hashSet, Matchers.hasEntry("template", Util.readResource("/plugin-settings.template.html")));
    }

    @Test
    public void allFieldsShouldBePresentInView() throws Exception {
        String template = Util.readResource("/plugin-settings.template.html");

        assertThat(template, containsString("<div class=\"form_item_block\">\n" +
                "  Plugin is configured by YML files. Read more at <a href=\"https://github.com/matic-insurance/gocd-http-notifications-plugin\">github</a>\n" +
                "</div>"));
    }

}

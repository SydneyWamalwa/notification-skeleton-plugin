# GoCD HTTP Notifications plugin

Plugin to send GoCD Notifications for agent and stage status
to arbitrary number of endpoints via POST request

## Installing

Put compiled jar file from releases to GoCD plugins folder

## Configuration

Create yaml configuration file somewhere on your server.

```YAML
agent_status:
  enabled: true
  endpoints:
    - https://webhooks.company.com/path
    - https://alerts.company.com/agent

stage_status:
  enabled: true
  endpoints:
    - https://webhooks.company.com/path
```

Specify absolute path to the file in `GOCD_HTTP_NOTIFICATIONS_CONFIG` environment variable

### Configuration reload

Plugin will reload configuration at random intervals

## Building the code base

To build the jar, run `./gradlew clean test assemble`

## License

```plain
Copyright 2018 ThoughtWorks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

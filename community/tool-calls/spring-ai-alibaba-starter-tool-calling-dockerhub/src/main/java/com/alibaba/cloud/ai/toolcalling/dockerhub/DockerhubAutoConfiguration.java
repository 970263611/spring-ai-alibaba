/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.toolcalling.dockerhub;

import com.alibaba.cloud.ai.toolcalling.common.JsonParseTool;
import com.alibaba.cloud.ai.toolcalling.common.RestClientTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * @author dahua
 * @since 2025/07/14
 */
@Configuration
@ConditionalOnClass(DockerhubAutoConfiguration.class)
@ConditionalOnProperty(prefix = DockerhubConstants.CONFIG_PREFIX, name = "enabled", havingValue = "true",
		matchIfMissing = true)
@EnableConfigurationProperties(DockerhubProperties.class)
public class DockerhubAutoConfiguration {

	@Bean(name = DockerhubConstants.TOOL_NAME)
	@ConditionalOnMissingBean
	@Description("Search images and tags from dockerhub")
	public DockerhubService searchService(JsonParseTool jsonParseTool,
											   DockerhubProperties dockerhubProperties) {
		RestClientTool restClientTool = RestClientTool.builder(jsonParseTool, dockerhubProperties).build();
		return new DockerhubService(restClientTool);
	}

}

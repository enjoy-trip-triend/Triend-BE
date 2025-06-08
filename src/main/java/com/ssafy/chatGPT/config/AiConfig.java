package com.ssafy.chatGPT.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Value("${ssafy.ai.system-prompt}")
    private String systemPrompt;
	
	@Bean
	ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}
	
	@Bean
	ChatClient advisedChatClient(ChatClient.Builder builder, ChatMemory memory) {
		return builder.defaultSystem(systemPrompt)
				.defaultAdvisors(new MessageChatMemoryAdvisor(memory))
				.build();
	}
}

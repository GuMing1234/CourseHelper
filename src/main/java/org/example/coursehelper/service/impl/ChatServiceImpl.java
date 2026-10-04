package org.example.coursehelper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.coursehelper.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final RestClient restClient;
    private final String apiKey;

    public ChatServiceImpl(RestClient.Builder builder,
                       @Value("${deepseek.api-key}") String apiKey) {
        this.restClient = builder.baseUrl("https://api.deepseek.com").build();
        this.apiKey = apiKey;
    }

    @Override
    public String chat(String msg) {
        Map<String,Object> body = Map.of("model","deepseek-chat","messages",List.of(Map.of("role","user","content",msg),Map.of("role","system","content","你是一个 helpful的助手，用来帮助用户回答问题")));

        Map<String,Object> response = restClient.post().uri("/chat/completions").header("Authorization", "Bearer " + apiKey).body(body).retrieve().body(Map.class);
        log.info("response: {}",response);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        String answer = (String) message.get("content");

        return answer;
    }
}

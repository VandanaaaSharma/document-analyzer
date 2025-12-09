package com.docai.document_analyzer.service;

import com.docai.document_analyzer.config.OpenAIConfig;
import com.docai.document_analyzer.model.AnalysisResponse;
import com.docai.document_analyzer.model.ChatMessage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {

    private final OkHttpClient client = new OkHttpClient();
    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final OpenAIConfig config;

    public OpenAIService(OpenAIConfig config) {
        this.config = config;
    }

    // Analysis
    public AnalysisResponse generateAnalysis(String text) throws Exception {

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4o-mini");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject()
                .put("role", "user")
                .put("content",
                        "Return STRICT JSON:\n" +
                                "{ \"summary\":\"...\", \"keywords\":[\"..\"], \"sentiment\":\"...\" }\n" +
                                "TEXT:\n" + text)
        );

        json.put("messages", messages);

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + config.getApiKey())
                .post(RequestBody.create(json.toString(), MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();

        JSONObject result = new JSONObject(body);

        String content = result.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        JSONObject ai = new JSONObject(content);

        return new AnalysisResponse(
                ai.getString("summary"),
                ai.getJSONArray("keywords").toList().stream().map(Object::toString).toList(),
                ai.getString("sentiment"),
                null, 0, null
        );
    }

    // Chat
    public String chatWithDocument(String documentText, String question, List<ChatMessage> history) throws Exception {

        JSONArray messages = new JSONArray();

        // System
        messages.put(new JSONObject()
                .put("role", "system")
                .put("content", "You answer based ONLY on this document:\n" + documentText)
        );

        // History
        for (ChatMessage msg : history) {
            messages.put(new JSONObject().put("role", msg.getRole()).put("content", msg.getContent()));
        }

        // New question
        messages.put(new JSONObject().put("role", "user").put("content", question));

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4o-mini");
        json.put("messages", messages);

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + config.getApiKey())
                .post(RequestBody.create(json.toString(), MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();

        JSONObject result = new JSONObject(body);

        return result.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}

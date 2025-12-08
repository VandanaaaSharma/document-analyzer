package com.docai.document_analyzer.service;
import com.docai.document_analyzer.config.OpenAIConfig;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final OkHttpClient client = new OkHttpClient();
    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final OpenAIConfig config;

    public OpenAIService(OpenAIConfig config) {
        this.config = config;
    }

    public String generateAnalysis(String text) throws Exception {

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4.1-mini");
        json.put("messages", new JSONObject[]{
                new JSONObject()
                        .put("role", "user")
                        .put("content",
                                "Analyze this text. Provide:\n" +
                                "1. Summary\n" +
                                "2. Keywords\n" +
                                "3. Sentiment (Positive/Negative/Neutral)\n\nText:\n" + text)
        });

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + config.getApiKey())
                .post(RequestBody.create(json.toString(), MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string())
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}


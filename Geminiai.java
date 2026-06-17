import okhttp3.*;

public class Geminiai {
    public static void main(String[] args) throws Exception {

        String apiKey = System.getenv("GEMINI_API_KEY");
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String requestBody = """
                {
                    "contents": [{
                        "parts": [{
                            "text": "Say hello and explain what you can do!"
                        }]
                    }]
                }
                """;

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                requestBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

// Parse the JSON and get only the AI's text
        org.json.JSONObject json = new org.json.JSONObject(responseBody);
        String aiReply = json
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");

        System.out.println("Gemini says: " + aiReply);

    }
}


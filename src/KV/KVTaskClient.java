package KV;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private static URI uri;
    private String apiToken;

    public KVTaskClient(URI uri) {
        this.uri = uri;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/register/"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            this.apiToken = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время регистрации");
        }
    }

    public void put (String key, String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время сохранения");
        }
    }

    public String load(String key) {
        String result;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            result = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время загрузки");
            result =  null;
        }
        return result;
    }
}

package jls;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * JLS 50/50: Java 11+ - Standard HTTP Client API (JLS §4.3)
 * Demonstrates non-blocking HTTP request processing built on modern Java specifications.
 */
public class Ex50_StandardHttpClient {

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/get"))
                .GET()
                .build();

        // Async non-blocking request
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(status -> System.out.println("HTTP Response Status Code: " + status))
                .join();
    }
}

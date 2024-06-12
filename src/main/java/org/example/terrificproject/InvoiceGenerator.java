package org.example.terrificproject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InvoiceGenerator {
    String apiKey = "sk_kiAoeX93rLivVbpNODQtoR9lTB3FsWRK";

    public void printInvoice() {
        try {
            String requestBody = "";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://invoice-generator.com"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Authorization", "Bearer " + apiKey)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println(response + "invoice generated" + response.body());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}


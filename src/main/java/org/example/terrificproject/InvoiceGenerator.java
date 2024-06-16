package org.example.terrificproject;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InvoiceGenerator {
    String apiKey = "sk_kiAoeX93rLivVbpNODQtoR9lTB3FsWRK";

    public void printInvoice(String from, String to, String shipTo, String logo, int number, String date, String customFieldName, String customFieldValue, String itemName, int itemQuantity, int itemUnitCost) {
        try {
            InvoiceRequest invoiceRequest = new InvoiceRequest(from, to, shipTo, logo, number, date, new CustomField[]{new CustomField(customFieldName, customFieldValue)}, new Item[]{new Item(itemName, itemQuantity, itemUnitCost)});

            Gson gson = new Gson();
            String requestBody = gson.toJson(invoiceRequest);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://invoice-generator.com")).POST(HttpRequest.BodyPublishers.ofString(requestBody)).header("Authorization", "Bearer " + apiKey).header("Content-Type", "application/json").build();
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get("db/invoices/invoice.pdf")));
            if (response.statusCode() == 200) {
                System.out.println("Invoice generated");
            } else {
                System.out.println("Invoice not generated" + response.statusCode());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    class InvoiceRequest {
        String from;
        String to;
        String ship_to;
        String logo;
        int number;
        String date;
        CustomField[] custom_fields;
        Item[] items;

        InvoiceRequest(String from, String to, String ship_to, String logo, int number, String date, CustomField[] custom_fields, Item[] items) {
            this.from = from;
            this.to = to;
            this.ship_to = ship_to;
            this.logo = logo;
            this.number = number;
            this.date = date;
            this.custom_fields = custom_fields;
            this.items = items;
        }
    }

    class CustomField {
        String name;
        String value;

        CustomField(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    class Item {
        String name;
        int quantity;
        int unit_cost;

        Item(String name, int quantity, int unit_cost) {
            this.name = name;
            this.quantity = quantity;
            this.unit_cost = unit_cost;
        }
    }
}
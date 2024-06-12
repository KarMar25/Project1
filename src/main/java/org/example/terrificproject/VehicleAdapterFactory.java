package org.example.terrificproject;

import com.google.gson.*;

import java.lang.reflect.Type;

public class VehicleAdapterFactory implements JsonDeserializer<Vehicle> {
    @Override
    public Vehicle deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "Car" -> context.deserialize(jsonObject, Car.class);
            case "Motorcycle" -> context.deserialize(jsonObject, Motorcycle.class);
            case "Pickup" -> context.deserialize(jsonObject, Pickup.class);
            case "Camper" -> context.deserialize(jsonObject, Camper.class);
            default -> throw new JsonParseException("Unknown element type: " + type);
        };
    }
}
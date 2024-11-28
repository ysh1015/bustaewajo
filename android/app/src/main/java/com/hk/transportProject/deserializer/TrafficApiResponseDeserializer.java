package com.hk.transportProject.deserializer;

import android.util.Log;
import com.google.gson.*;
import com.hk.transportProject.response.TrafficApiResponse;
import java.lang.reflect.Type;

public class TrafficApiResponseDeserializer implements JsonDeserializer<TrafficApiResponse> {
    @Override
    public TrafficApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            
            if (!jsonObject.has("response")) {
                throw new JsonParseException("Missing 'response' object");
            }

            JsonObject responseObj = jsonObject.getAsJsonObject("response");
            TrafficApiResponse response = new TrafficApiResponse();
            TrafficApiResponse.Response responseData = new TrafficApiResponse.Response();

            if (responseObj.has("header")) {
                JsonObject header = responseObj.getAsJsonObject("header");
                TrafficApiResponse.Header headerData = new Gson().fromJson(header, TrafficApiResponse.Header.class);
                responseData.setHeader(headerData);
            }

            if (responseObj.has("body")) {
                JsonObject body = responseObj.getAsJsonObject("body");
                TrafficApiResponse.Body bodyData = new Gson().fromJson(body, TrafficApiResponse.Body.class);
                responseData.setBody(bodyData);
            }

            response.setResponse(responseData);
            return response;

        } catch (Exception e) {
            Log.e("TrafficDeserializer", "Parsing error: " + e.getMessage());
            throw new JsonParseException("Failed to parse TrafficApiResponse", e);
        }
    }
}

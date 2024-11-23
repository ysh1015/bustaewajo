package com.hk.transportProject.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// 버스 노선 응답
public class BusRouteResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        @SerializedName("header")
        private Header header;
        
        @SerializedName("body")
        private Body body;
        
        public Header getHeader() {
            return header;
        }
        
        public Body getBody() {
            return body;
        }
    }

    public static class Header {
        @SerializedName("resultCode")
        private String resultCode;
        
        public String getResultCode() {
            return resultCode;
        }
    }

    public static class Body {
        @SerializedName("items")
        private Items items;
        
        public Items getItems() {
            return items;
        }
    }

    public static class Items {
        @SerializedName("item")
        private List<BusRoute> routes;
        
        public List<BusRoute> getRoutes() {
            return routes;
        }
    }

    public static class BusRoute {
        @SerializedName("routeid")
        private String routeId;
        
        @SerializedName("routeno")
        private String routeNo;
        
        @SerializedName("routetp")
        private String routeType;
        
        public String getRouteId() {
            return routeId;
        }
        
        public String getRouteNo() {
            return routeNo;
        }
        
        public String getRouteType() {
            return routeType;
        }
    }

    public boolean isSuccessful() {
        return response != null && 
               response.header != null && 
               "00".equals(response.header.resultCode);
    }
} 
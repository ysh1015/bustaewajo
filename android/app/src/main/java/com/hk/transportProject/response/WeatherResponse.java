package com.hk.transportProject.response;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("response")
    public Response response;

    public static class Response {
        @SerializedName("header")
        public Header header;

        @SerializedName("body")
        public Body body;

        public static class Header {
            @SerializedName("resultCode")
            public String resultCode;

            @SerializedName("resultMsg")
            public String resultMsg;
        }

        public static class Body {
            @SerializedName("dataType")
            public String dataType;

            @SerializedName("items")
            public Items items;

            @SerializedName("numOfRows")
            public int numOfRows;

            @SerializedName("pageNo")
            public int pageNo;

            @SerializedName("totalCount")
            public int totalCount;

            public static class Items {
                @SerializedName("item")
                public List<Item> item;

                public static class Item {
                    @SerializedName("baseDate")
                    public String baseDate;

                    @SerializedName("baseTime")
                    public String baseTime;

                    @SerializedName("category")
                    public String category;

                    @SerializedName("nx")
                    public int nx;

                    @SerializedName("ny")
                    public int ny;

                    @SerializedName("fcstValue")
                    public String fcstValue;

                    @SerializedName("fcstDate")
                    public String fcstDate;

                    @SerializedName("fcstTime")
                    public String fcstTime;
                }
            }
        }
    }
}

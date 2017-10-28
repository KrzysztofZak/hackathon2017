package com.costrella.sp.sp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mike on 2017-10-28.
 */
public class RetrofitInit {

    public static RetrofitInit instance = null;

    public static RetrofitInit getInstance() {
        if(instance == null){
            instance = new RetrofitInit();
        }
        return instance;
    }

    public SpAPI getCechiniAPI() {
        Gson gson = new GsonBuilder()
//                .registerTypeHierarchyAdapter(byte[].class,
//                new ByteArrayToBase64TypeAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SpAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(SpAPI.class);
    }

//    // Using Android's base64 libraries. This can be replaced with any base64 library.
//    private class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
//        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
//        }
//
//        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
//            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
//        }
//    }
}

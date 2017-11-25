package com.squareup.lib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by liangzhenxiong on 2017/10/17.
 */

public enum GsonUtil {
    INSTANCE;
    private Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
            .create();

    public Gson getGson() {
        return gson;
    }

    private class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
//            System.out.println("rawtype" + rawType.getName());
            if (rawType.getName().equals("java.lang.String")) {
                return (TypeAdapter<T>) new StringNullAdapter();
            } else if (rawType.getName().equals("int") || rawType.getName().equals("java.lang.Integer")) {
                return (TypeAdapter<T>) new IntegerAdapter();
            } else if (rawType.getName().equals("java.util.List")) {
//                return (TypeAdapter<T>) new ListNullAdapter();
            }
            return null;
        }
    }

    private class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) {
            // TODO Auto-generated method stub
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return "";
                }
                String str = reader.nextString();
                if (str == null || str.length() == 0) {
                    return "";
                } else if (str.equals("null")) {
                    return "";
                }
                return str;
            } catch (IOException e) {
            }
            return "";
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    private class IntegerAdapter extends TypeAdapter<Integer> {
        @Override
        public void write(JsonWriter jsonWriter, Integer integer) throws IOException {
            if (integer == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(integer);
        }

        @Override
        public Integer read(JsonReader reader) {
            // TODO Auto-generated method stub
            JsonToken token = null;
            try {
                token = reader.peek();
                if (token == JsonToken.NULL) {
                    reader.nextNull();
                    return 0;
                }
                String str = reader.nextString();
                if (str == null || str.length() == 0) {
                    return 0;
                } else if (str.equals("null")) {
                    return 0;
                }
                try {
                    return Double.valueOf(str).intValue();
                } catch (NumberFormatException e) {
                    return 0;
                }

            } catch (IOException e) {
//                e.printStackTrace();
            }
            return 0;

        }

    }

}

package com.switchfly.targetprocess;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.GenericList;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class TargetProcessParser {

    private final Type assignableType;
    private final Gson gson;

    public TargetProcessParser() {
        super();
        assignableType = new TypeToken<GenericList<Assignable>>() {}.getType();
        gson = buildGson();
    }

    private Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String stringDate = json.getAsString(); //  /Date(1337176800000-0500)
                String date = StringUtils.substringBetween(stringDate, "/Date(", "-"); // 1337176800000
                //TODO String timeZone = StringUtils.substringBetween(stringDate, "-", ")");  // 0500
                return new Date(Long.valueOf(date));
            }
        });
        return gsonBuilder.create();
    }

    public List<Assignable> parseAssignables(InputStream inputStream) {
        GenericList<Assignable> assignableList = gson.fromJson(new InputStreamReader(inputStream), assignableType);
        return assignableList.getItems();
    }
}

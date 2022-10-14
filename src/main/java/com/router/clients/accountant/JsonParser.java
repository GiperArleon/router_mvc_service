package com.router.clients.accountant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.router.clients.model.TimeRecord;
import com.router.clients.model.UserRecord;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    Gson gson;
    Type TimeRecordListType;
    Type UserRecordListType;

    public JsonParser() {
        TimeRecordListType = new TypeToken<ArrayList<TimeRecord>>(){}.getType();
        UserRecordListType = new TypeToken<ArrayList<UserRecord>>(){}.getType();

        gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
                .create();
    }

    public TimeRecord getTimeRecordFromJson(String response, Class<TimeRecord> tClass) {
        return gson.fromJson(response, tClass);
    }

    public UserRecord getUserRecordFromJson(String response, Class<UserRecord> tClass) {
        return gson.fromJson(response, tClass);
    }

    public List<TimeRecord> getListOfTimeRecordsFromJson(String response) {
        return new Gson().fromJson(response, TimeRecordListType);
    }

    public List<UserRecord> getListOfUserRecordsFromJson(String response) {
        return new Gson().fromJson(response, UserRecordListType);
    }
}

package org.example.entity;

import com.google.gson.Gson;
import org.example.Utils.GsonBuilder;

public abstract class AbstractEntity implements IIdentifier{

    private final Gson gson = new Gson();

    public String toJson() {
        return GsonBuilder.getGjon().toJson(this);
    }
}

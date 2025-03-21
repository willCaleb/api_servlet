package org.example.Utils;

import com.google.gson.Gson;
import com.google.gson.Strictness;
import org.example.entity.AbstractEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;

public class GsonBuilder {

    public static Gson getGjon() {
        return new com.google.gson.GsonBuilder()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setStrictness(Strictness.LENIENT)
                .create();
    }

    public static AbstractEntity toEntityFromRequest(HttpServletRequest request, Class<?> clazz) {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonRequest = jsonBuilder.toString();

            return (AbstractEntity) getGjon().fromJson(jsonRequest, clazz);
        }catch (Exception e) {
            throw new RuntimeException("Não foi possível realizar a operação: " + e.getMessage());
        }
    }

}

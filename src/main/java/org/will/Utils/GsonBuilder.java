package org.will.Utils;

import com.google.gson.Gson;
import com.google.gson.Strictness;
import org.will.model.dto.AbstractDTO;
import org.will.model.entity.AbstractEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class GsonBuilder {

    public static Gson getGjon() {
        return new com.google.gson.GsonBuilder()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
                .setExclusionStrategies(new IgnoreFieldExclusionStrategy())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setStrictness(Strictness.LENIENT)
                .create();
    }

    public static <E extends AbstractEntity> E toEntityFromRequest(HttpServletRequest request, Class<E> clazz) {
        try {
            String jsonRequest = getJsonString(request);

            return getGjon().fromJson(jsonRequest, clazz);
        }catch (Exception e) {
            throw new RuntimeException("Não foi possível realizar a operação: " + e.getMessage());
        }
    }

    private static String getJsonString(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonRequest = jsonBuilder.toString();
        return jsonRequest;
    }

    public static <DTO extends AbstractDTO> DTO fromRequestToDTO(HttpServletRequest request, Class<DTO> dtoClass) {
        try {
            String jsonString = getJsonString(request);

            return getGjon().fromJson(jsonString, dtoClass);
        }catch (Exception e) {
            throw new RuntimeException("Não foi possível realizar a operação: " + e.getMessage());
        }
    }
}

package org.will.servlets;

import org.will.Utils.GsonBuilder;
import org.will.Utils.Utils;
import org.will.converter.Converter;
import org.will.model.dto.AbstractDTO;
import org.will.model.entity.AbstractEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractServlet<E extends AbstractEntity, DTO extends AbstractDTO> extends HttpServlet {

    public void write(HttpServletResponse response, AbstractEntity entity) throws IOException {
        setConfigs(response);
        response.getWriter().write(toJson(Converter.toDto(entity, getDTOClass())));
    }

    public void write(HttpServletResponse response, List<E> entityList) throws IOException {
        setConfigs(response);
        response.getWriter().write(toJson(Converter.toDto(entityList, getDTOClass())));
    }

    public E toEntityFromRequest(HttpServletRequest request) {
        return (E) GsonBuilder.toEntityFromRequest(request, getEntityClass());
    }

    public DTO fromRequestToDTO(HttpServletRequest request) {
        return GsonBuilder.fromRequestToDTO(request, getDTOClass());
    }

    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class<E>) genericTypes[0];
    }

    @SuppressWarnings("unchecked")
    public Class<DTO> getDTOClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class<DTO>) genericTypes[1];
    }

    public static void setConfigs(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public String toJson(Object object) {
        return GsonBuilder.getGjon().toJson(object);
    }

    public Integer getId(HttpServletRequest request) {
        try {
            String pathInfo = request.getPathInfo();

            String stringId = pathInfo.substring(1);

            return Integer.parseInt(stringId);

        }catch (Exception ignored) {

        }
        return null;
    }
}


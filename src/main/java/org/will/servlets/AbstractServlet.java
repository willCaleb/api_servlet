package org.will.servlets;

import org.will.Constants.Constants;
import org.will.Utils.CustomGsonBuilder;
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

    public void writeBean(HttpServletResponse response, Object bean) throws IOException{
        setConfigs(response);
        response.getWriter().write(toJson(bean));
    }

    public void write(HttpServletResponse response, List<E> entityList) throws IOException {
        setConfigs(response);
        response.getWriter().write(toJson(Converter.toDto(entityList, getDTOClass())));
    }

    public E toEntityFromRequest(HttpServletRequest request) {
        return (E) CustomGsonBuilder.toEntityFromRequest(request, getEntityClass());
    }

    public DTO fromRequestToDTO(HttpServletRequest request) {
        return CustomGsonBuilder.fromRequestToDTO(request, getDTOClass());
    }

    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        Type[] genericTypes = getTypes();
        return (Class<E>) genericTypes[0];
    }

    @SuppressWarnings("unchecked")
    public Class<DTO> getDTOClass() {
        Type[] genericTypes = getTypes();
        return (Class<DTO>) genericTypes[1];
    }

    private Type[] getTypes() {
        return  ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
    }

    public static void setConfigs(HttpServletResponse response) {
        response.setContentType(Constants.HTTP_HEADER_CONTENT_TYPE);
        response.setCharacterEncoding(Constants.HTTP_HEADER_CHARACTER_ENCODING);
    }

    public String toJson(Object object) {
        return CustomGsonBuilder.getGjon().toJson(object);
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


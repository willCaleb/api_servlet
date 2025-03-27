package org.will.servlets;

import org.will.annotation.RequestMapping;
import org.will.converter.Converter;
import org.will.model.dto.CityDTO;
import org.will.model.entity.City;
import org.will.repository.impl.AbstractRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/city")
public class CityServlet extends AbstractServlet<City, CityDTO>{

    private final AbstractRepositoryImpl<City> cityRepository = new AbstractRepositoryImpl<>(City.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        City city = Converter.toEntity(fromRequestToDTO(request), City.class);

        write(response, city);
    }
}

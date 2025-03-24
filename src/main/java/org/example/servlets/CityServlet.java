package org.example.servlets;

import org.example.model.entity.City;
import org.example.repository.AbstractRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CityServlet extends AbstractServlet<City>{

    private static final String PATH = "/city";

    private final AbstractRepository<City> cityRepository = new AbstractRepository<>(City.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        City entityFromRequest = toEntityFromRequest(request);

        City city = new City();

        city.setName(entityFromRequest.getName());
        city.setUf(entityFromRequest.getUf());

        response.getWriter().write(toJson(cityRepository.save(city)));
    }
}

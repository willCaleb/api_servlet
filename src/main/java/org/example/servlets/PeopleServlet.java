package org.example.servlets;

import org.example.Utils.Utils;
import org.example.converter.Converter;
import org.example.model.dto.PeopleDTO;
import org.example.model.entity.Address;
import org.example.model.entity.People;
import org.example.repository.AbstractRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PeopleServlet extends AbstractServlet<People>{

    public static final String PATH = "/people";

    private final AbstractRepository<People> pessoaRepository = new AbstractRepository<>(People.class);

    public PeopleServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        People peopleRequest = toEntityFromRequest(request);

        People people = new People();

        resolverEnderecos(people, peopleRequest);

        people.setName(peopleRequest.getName());

        People save = pessoaRepository.save(people);

        response.getWriter().write(toJson(Converter.toDto(save, PeopleDTO.class)));
    }

    private void resolverEnderecos(People people, People peopleRequest) {
        if (Utils.isNotEmpty(peopleRequest.getAddresses())) {
            for (Address address : peopleRequest.getAddresses()) {
                address.setPeople(people);
            }
            people.setAddresses(peopleRequest.getAddresses());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        int id = getId(request);

        People people = pessoaRepository.findById(id);

        PeopleDTO dto = Converter.toDto(people, PeopleDTO.class);

        response.getWriter().write(toJson(dto));
    }
}

package org.will.servlets;

import org.will.Utils.Utils;
import org.will.annotation.RequestMapping;
import org.will.converter.Converter;
import org.will.model.dto.PeopleDTO;
import org.will.model.entity.Address;
import org.will.model.entity.People;
import org.will.repository.impl.AbstractRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/people")
public class PeopleServlet extends AbstractServlet<People, PeopleDTO>{

    private final AbstractRepositoryImpl<People> pessoaRepository = new AbstractRepositoryImpl<>(People.class);

    public PeopleServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        People people = Converter.toEntity(fromRequestToDTO(request), People.class);

        resolverEnderecos(people, people);

        People save = pessoaRepository.save(people);

        write(response, save);
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
        int id = getId(request);

        People people = pessoaRepository.findById(id);

        write(response, people);
    }
}

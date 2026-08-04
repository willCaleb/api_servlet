package org.will.servlets;

import org.will.Utils.Utils;
import org.will.annotation.RequestMapping;
import org.will.converter.Converter;
import org.will.model.dto.PeopleDTO;
import org.will.model.entity.Address;
import org.will.model.entity.Person;
import org.will.repository.AbstractRepository;
import org.will.repository.impl.AbstractRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/people")
public class PeopleServlet extends AbstractServlet<Person, PeopleDTO>{

    private final AbstractRepository<Person> pessoaRepository = new AbstractRepositoryImpl<>(Person.class);

    public PeopleServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = Converter.toEntity(fromRequestToDTO(request), Person.class);

        resolverEnderecos(person);

        Person save = pessoaRepository.save(person);

        write(response, save);
    }

    private void resolverEnderecos(Person person) {
        if (Utils.isNotEmpty(person.getAddresses())) {
            for (Address address : person.getAddresses()) {
                address.setPerson(person);
            }
            person.setAddresses(person.getAddresses());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);

        Person person = pessoaRepository.findById(id);

        write(response, person);
    }
}

package org.example.servlets;

import org.example.Utils.Utils;
import org.example.entity.People;
import org.example.repository.AbstractRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PessoaServlet extends AbstractServlet<People>{

    public static final String PATH = "/pessoa";

    private final AbstractRepository<People> pessoaRepository = new AbstractRepository<>();

    public PessoaServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        People peopleRequest = toEntityFromRequest(request);

        People people = new People();

        resolverEnderecos(people, peopleRequest);

        people.setNome(peopleRequest.getNome());

        response.getWriter().write(toJson(pessoaRepository.save(people)));
    }

    private void resolverEnderecos(People people, People peopleRequest) {
        if(Utils.isNotEmpty(peopleRequest.getAddresses())) {
            people.setAddresses(peopleRequest.getAddresses());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        int id = getId(request);

        response.getWriter().write(toJson(pessoaRepository.findById(id)));
    }

    private static int getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        String stringId = pathInfo.substring(1);

        return Integer.parseInt(stringId);
    }
}

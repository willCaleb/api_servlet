package org.example.servlets;

import org.example.Utils.GsonBuilder;
import org.example.Utils.Utils;
import org.example.entity.Pessoa;
import org.example.repository.AbstractRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PessoaServlet extends AbstractServlet{

    public static final String PATH = "/pessoa";

    private AbstractRepository<Pessoa> pessoaRepository = new AbstractRepository<>(Pessoa.class);

    public PessoaServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        Pessoa pessoaRequest = (Pessoa) GsonBuilder.toEntityFromRequest(request, Pessoa.class);

        Pessoa pessoa = new Pessoa();

        resolverEnderecos(pessoa, pessoaRequest);

        pessoa.setNome(pessoaRequest.getNome());

        response.getWriter().write(pessoaRepository.save(pessoa).toJson());
    }

    private void resolverEnderecos(Pessoa pessoa, Pessoa pessoaRequest) {
        if(Utils.isNotEmpty(pessoaRequest.getEnderecos())) {
            pessoa.setEnderecos(pessoaRequest.getEnderecos());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setConfigs(response);

        int id = getId(request);

        response.getWriter().write(pessoaRepository.findById(id).toJson());
    }

    private static int getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        String stringId = pathInfo.substring(1);

        return Integer.parseInt(stringId);
    }


}

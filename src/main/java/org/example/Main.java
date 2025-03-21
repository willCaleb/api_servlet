package org.example;

import org.example.entity.Pessoa;
import org.example.repository.AbstractRepository;
import org.example.server.JettyInitializer;

public class Main {


    public static void main(String[] args) {
        JettyInitializer.start();
    }

    private static void findById(int id) {
        AbstractRepository<Pessoa> abstractRepository = new AbstractRepository<>(Pessoa.class);

        Pessoa pessoa = abstractRepository.findById(id);

        System.out.println(pessoa.getNome());
    }

    private static void criar() {
        AbstractRepository<Pessoa> abstractRepository = new AbstractRepository<>(Pessoa.class);

        Pessoa pes = new Pessoa();

        pes.setNome("Abigail teste 3");

        abstractRepository.save(pes);
    }

}
package br.com.projeto.maximatech.repositorio;

import org.springframework.data.repository.CrudRepository;

import br.com.projeto.maximatech.modelo.Cliente;


public interface Repositorio extends CrudRepository<Cliente, Long>{

    Iterable<Cliente> findByNomeAndCnpj(String nome, String cnpj);

    Iterable<Cliente> findByNome(String nome);

    Iterable<Cliente> findByCnpj(String cnpj);
    
}

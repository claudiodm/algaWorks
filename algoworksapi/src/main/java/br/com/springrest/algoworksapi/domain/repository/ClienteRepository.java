package br.com.springrest.algoworksapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springrest.algoworksapi.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    //pesquisa com o nome exato
    List<Cliente> findByNome(String nome);
    //pesquisa por palavras
    List<Cliente> findByNomeContaining(String nome);

    Optional<Cliente> findByEmail(String email);

    //usando como teste
    List<Cliente> findByNomeContains(String nome);
    List<Cliente> findByNomeIsContaining(String nome);
    List<Cliente> findByNomeLike(String nome);
}

package br.com.springrest.algoworksapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.springrest.algoworksapi.domain.exception.NegocioException;
import br.com.springrest.algoworksapi.domain.model.Cliente;
import br.com.springrest.algoworksapi.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CatalogoClienteService {
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        // Se cliente que encontrou no repositório for diferente do que estamos salvando, ai dá match que é true
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
        .stream()
        .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
       
        if(emailEmUso){
            throw new NegocioException("Já existe um cliente cadastrado com este email!!!");
        }
        return clienteRepository.save(cliente);

    }

    @Transactional
    public void excluir(Long clienteId){
        clienteRepository.deleteById(clienteId);
    }
}

package br.com.springrest.algoworksapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springrest.algoworksapi.domain.model.Cliente;
import br.com.springrest.algoworksapi.domain.repository.ClienteRepository;
import br.com.springrest.algoworksapi.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;

@AllArgsConstructor // @Autowired --> já coloca automaticamente o construtor e o autowired
@RestController
@RequestMapping("/clientes")
public class ClienteController {
     

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

/* 
 * Listar/PESQUISAR de várias formas pode receber parâmetro também
 */

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @GetMapping("/clientes1")
    public List<Cliente> listar1(){
       return clienteRepository.findByNomeContaining("Ma");      
    }
    @GetMapping("/clientes2")
    public List<Cliente> listar2(){
       return clienteRepository.findByNomeContains("sa");      
    }
    @GetMapping("/clientes3")
    public List<Cliente> listar3(){
       return clienteRepository.findByNomeIsContaining("Ma");      
    }
    @GetMapping("/clientes4")
    public List<Cliente> listar4(){
       return clienteRepository.findByNomeLike("%Ma%");      
    }


/* 
 * PESQUISAR de várias formas
 */
    // @PathVariable --> vincula o parâmetro do método a variável.
    @GetMapping("/{clienteid}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteid){

        return clienteRepository.findById(clienteid)
        //.map(cliente -> ResponseEntity.ok(cliente))
        .map(ResponseEntity :: ok)
        .orElse(ResponseEntity.notFound().build());
    //     Optional<Cliente> cliente = clienteRepository.findById(clienteid);
    //     // orElse --> retorna o valor ou NULL
    //     // return cliente.orElse(null);

    //     if (cliente.isPresent()){
    //         return ResponseEntity.ok(cliente.get());
    //     }
    //     return ResponseEntity.notFound().build();
     }



     /* 
 * Adicionar
 */
     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        // return clienteRepository.save(cliente);
        return catalogoClienteService.salvar(cliente);
     }



     /* 
 * ATUALIZAR   
 */
     @PutMapping("/{clienteId}")
     public ResponseEntity<Cliente> atualizar(@PathVariable long clienteId,
     @Valid @RequestBody Cliente cliente){
            if(!clienteRepository.existsById(clienteId)){
                return ResponseEntity.notFound().build();
            }
            cliente.setId(clienteId);
            cliente = catalogoClienteService.salvar(cliente);
            // cliente = clienteRepository.save(cliente);

            return ResponseEntity.ok(cliente);
     }


     /* 
 * DELETAR
 */
    @DeleteMapping("/{clienteId}")
     public ResponseEntity<Void> remover(@PathVariable Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }
        // clienteRepository.deleteById(clienteId);
        catalogoClienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
        
     }
}

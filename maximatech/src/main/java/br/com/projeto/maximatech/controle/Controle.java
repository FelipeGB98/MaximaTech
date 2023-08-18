package br.com.projeto.maximatech.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.maximatech.modelo.Cliente;
import br.com.projeto.maximatech.repositorio.Repositorio;

@RestController
@CrossOrigin(origins = "*")
public class Controle {

    @Autowired
    private Repositorio acao;

   // @PostMapping("/")
    //public Cliente cadastrar(@RequestBody Cliente c){
     //   return acao.save(c);
   // }

    @PostMapping("/")
    public ResponseEntity<?> cadastrar(@RequestBody Cliente c) {
        if (clienteValido(c)) {
            Cliente savedClient = acao.save(c);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalido", HttpStatus.BAD_REQUEST);
        }
    }

    //verifica se tem nome valido
    private boolean clienteValido(Cliente c) {
        return c.getNome() != null && !c.getNome().isEmpty() &&
               c.getCnpj() != 0L && cnpjValido(c.getCnpj());
    }
    
    //verifica se tem nome cnpj valido
    private boolean cnpjValido(long cnpj) {
        String cnpjString = String.valueOf(cnpj);
        return cnpjString.matches("\\d{14}");
    }
    
    
    @GetMapping("/")
    public Iterable<Cliente> selecionar(@RequestParam(required = false) String nome,
                                        @RequestParam(required = false) String cnpj) {
        if (nome != null && cnpj != null) {
            return acao.findByNomeAndCnpj(nome, cnpj);
        } else if (nome != null) {
            return acao.findByNome(nome);
        } else if (cnpj != null) {
            return acao.findByCnpj(cnpj);
        } else {
            return acao.findAll();
        }
    }

    @PutMapping("/")
    public Cliente editar(@RequestBody Cliente c){
        return acao.save(c);
    }

    @DeleteMapping("/{codigo}")
    public void remover(@PathVariable long codigo){
        acao.deleteById(codigo);
    }


}

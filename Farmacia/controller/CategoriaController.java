package com.generation.farmacia.Farmacia.controller;

import com.generation.farmacia.Farmacia.model.Categoria;
import com.generation.farmacia.Farmacia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    private ResponseEntity<List<Categoria>> getAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id){
        return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Categoria>> getByTipo(@PathVariable String tipo){
        return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
    }

    //cadastrar
    @PostMapping
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoriaRepository.save(categoria));
    }


    //Atualizar - update(Mysql)
    @PutMapping
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())
        .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                .body(categoriaRepository.save(categoria)))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //Deletar produto
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty())
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoriaRepository.deleteById(id);
    }

}

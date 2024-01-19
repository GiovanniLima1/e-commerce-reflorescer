package com.generation.reflorescer.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.reflorescer.model.Categoria;
import com.generation.reflorescer.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/*
	-> getAll = pega tudo
	-> getById = pega apenas o Id 
	-> getByNome = pega o nome
	-> ResponseEntity = armazena as respostas http
	-> ok(200) um dos status do ResponseEntity
	-> findAll = Encontrar tudo (estamos usando a biblioteca)
	*/
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.ok(categoriaRepository.findAll());  
	}
	/*
	 -> map = mapear os dados
	 '->' = lambda
	 se encontar o Id mapeia se não faz a linha do orElse
	 orElse = Se não (se não deu certo a linha de cima, fazer a linha do orElse)
	 NOT FOUND = status http que diz que não foi encontrado
	 Build = construir 
	*/
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nomeCategoria/{nome}")
	public ResponseEntity<List<Categoria>> getByNomeCategoria(@PathVariable String nome){
		return ResponseEntity.ok(categoriaRepository
				.findAllByNomeCategoriaContainingIgnoreCase(nome));
	}
	// NotNull = [     ]
	// NotBlank = [obrigado a passar algo]
	// Valid = Valida 
	// RequestBody = corpo da requisição
	// Created = status criado
	// body = corpo da requisição
	// save = salva 
	
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaRepository.save(categoria));
	}
	
	//iremos atualizar pelo Id
	// get = pega
	
	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
		return categoriaRepository.findById(categoria.getId())
				.map(resp -> ResponseEntity.status(HttpStatus.OK)
						.body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	/* 
	   -> void = não retornamos nada, usamos o ResponseStatus
	   -> isEmpty = está vazio
	   -> O
	*/
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		if(categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	
		categoriaRepository.deleteById(id);	
	}
}

package com.generation.reflorescer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.reflorescer.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	/*
		-> interface = contrato, tudo que iremos passar aqui será obrigatório a implementação
		-> é tipo a pesquisa que fazemos no MySQL (Select ...)
		-> IgnoreCase = não importa se é maiusculo ou minusculo
		-> estamos fazendo a assinatura do método pq a JPA pesquisa só pelo Id, então temos que fazer na 'mão'
		-> Param = parametro que será buscado 
	*/
	
	List<Categoria>findAllByNomeCategoriaContainingIgnoreCase(@Param("nomeCategoria") String nome);
}

package com.estpoofelipe.ajudaanimal.models;

import org.springframework.web.multipart.MultipartFile;

import com.estpoofelipe.ajudaanimal.enums.Especie;

import jakarta.validation.constraints.*;

public class AnimalDto {

	private String nome;
	
	@NotEmpty(message = "A raça do animal precisa ser informada!")
	private String raca;
	
	private Especie especie;
	
	@Min(0)
	private int idade;
	
	@Size(min = 10, message = "Informe a descrição do animal!")
	@Size(max = 2000, message = "A descrição está muito longa! - Limite 2000 caracteres!")
	private String descricao;
	
	private MultipartFile imagem;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}
}

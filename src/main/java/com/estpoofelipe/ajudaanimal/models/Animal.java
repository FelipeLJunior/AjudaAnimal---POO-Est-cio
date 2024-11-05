package com.estpoofelipe.ajudaanimal.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "animais")
public class Animal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	private String raca;
	private String especie;
	private int idade;
	private String nomeFoto;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private Date dtCad;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getNomeFoto() {
		return nomeFoto;
	}
	public void setNomeFoto(String nomeFoto) {
		this.nomeFoto = nomeFoto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDtCad() {
		return dtCad;
	}
	public void setDtCad(Date dtCad) {
		this.dtCad = dtCad;
	}
}

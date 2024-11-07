package com.estpoofelipe.ajudaanimal.controllers;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.estpoofelipe.ajudaanimal.enums.Especie;
import com.estpoofelipe.ajudaanimal.models.Animal;
import com.estpoofelipe.ajudaanimal.models.AnimalDto;
import com.estpoofelipe.ajudaanimal.services.AnimaisRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/animais")
public class AnimaisController {

	@Autowired
	private AnimaisRepository repo;
	
	@GetMapping({"", "/"})
	public String mostrarListaAnimais(Model model) {
		List<Animal> animais = repo.findAll();
		model.addAttribute("animais", animais);
		return "animais/index";
	}
	
	@GetMapping({"/cadastrar"})
	public String mostrarPaginaCriacao(Model model) {
		AnimalDto animalDto = new AnimalDto();
		model.addAttribute("animalDto", animalDto);
		return "animais/CadastrarAnimal";
	}
	
	@PostMapping({"/cadastrar"})
	public String cadastrarAnimal(@Valid @ModelAttribute AnimalDto animalDto, BindingResult result) {
		
		if(result.hasErrors()) {
			return "animais/CadastrarAnimal";
		}
		
		Animal animal = new Animal();
		Date dtCad = new Date();
		
		if(!animalDto.getImagem().isEmpty()) {
			MultipartFile imagem = animalDto.getImagem();
			String nomeDaImagem = dtCad.getTime() + "_" + imagem.getOriginalFilename();
			
			try {
				String pastaUpload = "public/imagens/";
				Path uploadCaminho = Paths.get(pastaUpload);
				
				if(!Files.exists(uploadCaminho)) {
					Files.createDirectories(uploadCaminho);
				}
				
				try (InputStream inputStream = imagem.getInputStream()) { 
					Files.copy(inputStream, Paths.get(pastaUpload + nomeDaImagem), 
							StandardCopyOption.REPLACE_EXISTING);
				}				
				animal.setNomeFoto(nomeDaImagem);
				
			} catch(Exception e) {
				System.out.println("Exception: " + e.getMessage());
			}			
		}
		
		
		animal.setNome(animalDto.getNome());
		animal.setRaca(animalDto.getRaca());
		animal.setEspecie(animalDto.getEspecie());
		animal.setIdade(animalDto.getIdade());
		animal.setDescricao(animalDto.getDescricao());
		animal.setDtCad(dtCad);
		
		repo.save(animal);
		
		return "redirect:/animais";
	}
	
	@GetMapping("/editar")
	public String mostrarPaginaEdicao(Model model, @RequestParam int id) {
		
		try {
			
			Animal animal = repo.findById(id).get();
			model.addAttribute("animal", animal);

			AnimalDto animalDto = new AnimalDto();
			animalDto.setNome(animal.getNome());
			animalDto.setRaca(animal.getRaca());
			animalDto.setEspecie(animal.getEspecie());
			animalDto.setIdade(animal.getIdade());
			animalDto.setDescricao(animal.getDescricao());
			
			model.addAttribute("animalDto", animalDto);
		} catch(Exception e) {
			System.out.println("Mensagem: " + e.getMessage());
			return "redirect:/animais";
		}
		
		return "animais/EditarInformacaoAnimal";
	}
	
	@PostMapping("/editar")
	public String editarAnimal(Model model, @RequestParam int id, 
			@Valid @ModelAttribute AnimalDto animalDto, BindingResult result) {
		
		try {
			Animal animal = repo.findById(id).get();
			model.addAttribute("animal", animal);
			
			if(result.hasErrors()) {
				return "animais/EditarInformacaoAnimal";
			}
			
			if(!animalDto.getImagem().isEmpty()) {
				String diretorio = "public/imagens/";
				Path antigoCaminhoImagem = Paths.get(diretorio + animal.getNomeFoto());
				
				try {
					Files.delete(antigoCaminhoImagem);
				} catch (Exception e) {
					System.out.println("Mensagem: " + e.getMessage());					
				}
				
				MultipartFile imagem = animalDto.getImagem();
				Date dtAlt = new Date();
				String nomeDaImagem = dtAlt.getTime() + "_" + imagem.getOriginalFilename();
				
				try (InputStream inputStream = imagem.getInputStream()) { 
					Files.copy(inputStream, Paths.get(diretorio + nomeDaImagem), 
							StandardCopyOption.REPLACE_EXISTING);
				}
				
				animal.setNomeFoto(nomeDaImagem);
			}
			
			animal.setNome(animalDto.getNome());
			animal.setRaca(animalDto.getRaca());
			animal.setEspecie(animalDto.getEspecie());
			animal.setIdade(animalDto.getIdade());
			animal.setDescricao(animalDto.getDescricao());
			
			repo.save(animal);
			
		} catch (Exception e) {
			System.out.println("Mensagem: " + e.getMessage());
		}
		
		return "redirect:/animais";
	}
	
	@GetMapping("/deletar")
	public String deletarRegistroAnimal(@RequestParam int id) {
		
		try {
			Animal animal = repo.findById(id).get();
			
			Path caminhoImagem = Paths.get("public/imagens/" + animal.getNomeFoto());
			
			try {
				Files.delete(caminhoImagem);
			} catch (Exception e) {
				System.out.println("Mensagem: " + e.getMessage());				
			}
			
			repo.delete(animal);
			
		} catch (Exception e) {
			System.out.println("Mensagem: " + e.getMessage());
		}
		
		
		return "redirect:/animais";
	}
}

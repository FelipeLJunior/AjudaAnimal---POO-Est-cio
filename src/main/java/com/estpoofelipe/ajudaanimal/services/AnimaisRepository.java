package com.estpoofelipe.ajudaanimal.services;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estpoofelipe.ajudaanimal.models.Animal;

public interface AnimaisRepository extends JpaRepository<Animal, Integer> {
	
}

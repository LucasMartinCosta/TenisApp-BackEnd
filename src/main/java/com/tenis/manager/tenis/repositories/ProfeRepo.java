package com.tenis.manager.tenis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenis.manager.tenis.entities.Profesor;

public interface ProfeRepo extends JpaRepository<Profesor, Long>{

}

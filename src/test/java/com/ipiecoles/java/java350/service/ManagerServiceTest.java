package com.ipiecoles.java.java350.service;

import java.util.HashSet;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ipiecoles.java.java350.model.Manager;
import com.ipiecoles.java.java350.model.Technicien;
import com.ipiecoles.java.java350.repository.ManagerRepository;
import com.ipiecoles.java.java350.repository.TechnicienRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerServiceTest {

	@Autowired
	ManagerService managerService;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	TechnicienRepository technicienRepository;
	
	@Before
	public void setUp() {
		technicienRepository.deleteAll();
		managerRepository.deleteAll();
	}
	
	@Test
	public void testAddTechniciens() {
		//Given
		Manager manager = new Manager("Durand","Jean", "M12345",null,2000d,new HashSet<>());
		manager = managerRepository.save(manager);
		Manager manager2 = new Manager("Dupre","Jacques", "M12346",null,2000d,new HashSet<>());
		manager2 = managerRepository.save(manager2);

		Technicien technicien = new Technicien("Dupond","Jacques", "T98765", null, 1000d, 2);
		technicien = technicienRepository.save(technicien);
		Technicien technicien2 = new Technicien("Dupre","Jean", "T98764", null, 1000d, 2);
		technicien2 = technicienRepository.save(technicien2);

		//When
		managerService.addTechniciens(manager.getId(), technicien.getMatricule());
		//managerService.addTechniciens(manager2.getId(), technicien2.getMatricule());

		
		//Then
		Manager finalManager = managerRepository.findOneWithEquipeById(manager.getId());
		
		Technicien finalTechnicien = technicienRepository.findOne(technicien.getId());
		
		Manager finalManager2 = managerRepository.findOneWithEquipeById(manager2.getId());
		
		Technicien finalTechnicien2 = technicienRepository.findOne(technicien2.getId());
		
		Assertions.assertThat(finalManager.getEquipe().contains(finalTechnicien));
		Assertions.assertThat(finalTechnicien.getManager()).isNotNull();
		Assertions.assertThat(finalTechnicien.getManager()).isEqualTo(finalManager);
		Assertions.assertThat(finalTechnicien2.getManager()).isNull();
		Assertions.assertThat(finalManager2.getEquipe()).isEmpty();
	}

	@Test
	public void testAddTechnicienWithNoManager() {
		//Given
		Technicien technicien2 = new Technicien("Dupre","Jean", "T98764", null, 1000d, 2);
		technicien2 = technicienRepository.save(technicien2);
		
		//When
		try {
			managerService.addTechniciens(null, technicien2.getMatricule());
			Assertions.fail("Cela aurait du planter");
		}
		catch(Exception e){
			//Then
			Assertions.assertThat(e).isInstanceOf(EntityNotFoundException.class);
			Assertions.assertThat(e).hasMessage("Impossible de trouver le manager d'identifiant null");
			//Assertions.assertThat(e.getMessage()).isEqualToIgnoringWhitespace(expected)("Impossible de trouver le manager d'identifiant null");
		}
	}

}

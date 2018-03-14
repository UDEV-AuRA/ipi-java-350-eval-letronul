package com.ipiecoles.java.java350.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Employe;

import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeRepositoryTest {
	
	//Exercice sur la méthode FindEmployePlusRiche
	
	@Autowired
	private EmployeRepository employeRepository;
	
	public Commercial pierreDurand, rachidDurand, manuelPierre;
	
	@Before
	public void setup(){
		employeRepository.deleteAll();
		
		pierreDurand = new Commercial();
		pierreDurand.setPrenom("Pierre");
		pierreDurand.setNom("Durand");
		pierreDurand.setSalaire(1000d);
		pierreDurand = employeRepository.save(pierreDurand);	
		
		rachidDurand = new Commercial();
		rachidDurand.setPrenom("Rachid");
		rachidDurand.setNom("Durand");
		rachidDurand.setSalaire(10000d);
		rachidDurand = employeRepository.save(rachidDurand);	
		
		manuelPierre = new Commercial();
		manuelPierre.setPrenom("Manuel");
		manuelPierre.setNom("Pierre");
		manuelPierre.setSalaire(2000d);
		manuelPierre = employeRepository.save(manuelPierre);	
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_DoubleOccurence(){
		//GIVEN

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Durand");
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).hasSize(2);
		Assertions.assertThat(employeList).contains(pierreDurand, rachidDurand);
		
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_nomAndPrenom(){
		//GIVEN

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).hasSize(2);
		Assertions.assertThat(employeList).contains(pierreDurand, manuelPierre);
		
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_nomNonRepertorier(){
		//GIVEN

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Valls");
	
		//THEN
		Assertions.assertThat(employeList).isEmpty();
		
		
	}
	
	@Test
	public void testFindLePlusRicheRachid(){
		//GIVEN
		
		//WHEN
		List<Employe> employeList = employeRepository.findEmployePlusRiches();
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).contains(rachidDurand);
		
		
	}
	
	@Test
	public void testFindLePlusRicheTousEgaux(){
		//GIVEN
		pierreDurand.setSalaire(10000d);
		manuelPierre.setSalaire(10000d);
		
		//WHEN
		List<Employe> employeList = employeRepository.findEmployePlusRiches();
	
		//THEN
		Assertions.assertThat(employeList).isEmpty();
		Assertions.assertThat(employeList).doesNotContain(rachidDurand,pierreDurand,manuelPierre);
		
		
	}
	
	@Test
	public void testFindLePlusRichePlusieursRiches(){
		//GIVEN
		pierreDurand.setSalaire(10000d);
		manuelPierre.setSalaire(2000d);
		
		//WHEN
		List<Employe> employeList = employeRepository.findEmployePlusRiches();
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).contains(rachidDurand,pierreDurand);
		Assertions.assertThat(employeList).doesNotContain(manuelPierre);

		
		
	}
}

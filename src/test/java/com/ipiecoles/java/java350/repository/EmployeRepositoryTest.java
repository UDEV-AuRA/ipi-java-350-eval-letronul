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
	
	@Autowired
	private EmployeRepository employeRepository;
	
	public Commercial pierreDurand, rachidDurand, manuelPierre;
	
	@Before
	public void setup(){
		//GIVEN
		employeRepository.deleteAll();
		
		pierreDurand = new Commercial();
		pierreDurand.setPrenom("Pierre");
		pierreDurand.setNom("Durand");
		pierreDurand.setMatricule("A12345");
		pierreDurand = employeRepository.save(pierreDurand);	
		
		rachidDurand = new Commercial();
		rachidDurand.setPrenom("Rachid");
		rachidDurand.setNom("Durand");
		rachidDurand.setMatricule("B12345");
		rachidDurand = employeRepository.save(rachidDurand);	
		
		manuelPierre = new Commercial();
		manuelPierre.setPrenom("Manuel");
		manuelPierre.setNom("Pierre");
		manuelPierre.setMatricule("C12345");
		manuelPierre = employeRepository.save(manuelPierre);	
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_DoubleOccurence(){
		//..given

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Durand");
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).hasSize(2);
		Assertions.assertThat(employeList).contains(pierreDurand, rachidDurand);
		
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_nomAndPrenom(){
		//..given

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");
	
		//THEN
		Assertions.assertThat(employeList).isNotEmpty();
		Assertions.assertThat(employeList).hasSize(2);
		Assertions.assertThat(employeList).contains(pierreDurand, manuelPierre);
		
	}
	
	@Test
	public void test_FindNomOrPrenomAllIgnoreCase_nomNonRepertorier(){
		//..given

		
		//WHEN
		List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Valls");
	
		//THEN
		Assertions.assertThat(employeList).isEmpty();
		
		
	}
	
	@Test
	public void test_findEmployePlusRiches_casNominal(){
		//..given
		Employe employeA = employeRepository.findByMatricule("A12345");
		employeA.setSalaire(1000d);
		employeRepository.save(employeA);
		
		Employe employeB = employeRepository.findByMatricule("B12345");
		employeB.setSalaire(2000d);
		employeRepository.save(employeB);
		
		Employe employeC = employeRepository.findByMatricule("C12345");
		employeC.setSalaire(3000d);
		employeRepository.save(employeC);
		
		//WHEN
		
		List<Employe> employeListRiche = employeRepository.findEmployePlusRiches();
		
		
		//THEN
		Assertions.assertThat(employeListRiche).isNotEmpty();
		Assertions.assertThat(employeListRiche.size()).isEqualTo(1);
		Assertions.assertThat(employeListRiche).contains(employeC);
		Assertions.assertThat(employeListRiche.get(0).getSalaire()).isEqualTo(3000d);
		
	}
	
	
	@Test
	public void test_findEmployePlusRiches_2PlusRiches(){
		//..given
		Employe employeA = employeRepository.findByMatricule("A12345");
		employeA.setSalaire(1000d);
		employeRepository.save(employeA);
		
		Employe employeB = employeRepository.findByMatricule("B12345");
		employeB.setSalaire(3000d);
		employeRepository.save(employeB);
		
		Employe employeC = employeRepository.findByMatricule("C12345");
		employeC.setSalaire(3000d);
		employeRepository.save(employeC);
		
		//WHEN
		
		List<Employe> employeListRiche = employeRepository.findEmployePlusRiches();
		
		
		//THEN
		Assertions.assertThat(employeListRiche).isNotEmpty();
		Assertions.assertThat(employeListRiche.size()).isEqualTo(2);
		Assertions.assertThat(employeListRiche).contains(employeC,employeB);
		Assertions.assertThat(employeListRiche.get(0).getSalaire()).isEqualTo(3000d);
		Assertions.assertThat(employeListRiche.get(1).getSalaire()).isEqualTo(3000d);
	}
	
	
	@Test
	public void test_findEmployePlusRiches_emptyRepository(){
		//..given
		employeRepository.deleteAll();
		
		//WHEN
		
		List<Employe> employeListRiche = employeRepository.findEmployePlusRiches();
		
		
		//THEN
		Assertions.assertThat(employeListRiche).isEmpty();
	}
}

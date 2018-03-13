package com.ipiecoles.java.java350.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ManagerParameterizedAugmenterSalaireTest {
	
	@Parameter(value = 0)
	public Double salaireBase;
	
	@Parameter(value = 1)
	public Set<Technicien> equipeTest = new HashSet<>();
	
	@Parameter(value = 2)
	public Double salaireFinalManager;
	
	@Parameter(value = 3)
	public Double salaireFinalTechnicien;
	
	@Parameter(value = 4)
	public Double pourcentage;
	
	
	@Parameters(name = "Salaire de base du Manager {0} / Augmentation de {4} /Resultat : Salaire final Manager {2} / Salaire final Technicien {3}")
	public static Collection<Object[]> data() {
		
		//GIVEN
		Set<Technicien> equipe = new HashSet<>();
		equipe.add(new Technicien());
		equipe.add(new Technicien("bidon", "d'huile", null, null, null, null));
		equipe.add(new Technicien("bidon", "d'essence", null, null, null, null));
		equipe.add(new Technicien("bidon", "de friture", null, null, null, null));
		
		
		Set<Technicien> equipeEmpty = new HashSet<>();

	    return Arrays.asList(new Object[][]{
	           { 1000d, equipe,1700d},
	           { 1000d, equipeEmpty,1300d}
	    	}
	        );
	}
	
	@Test
	public void testEquivalenceSalaire(){
		Manager manager = new Manager();
		manager.setEquipe(equipeTest);
		
		//manager.setSalaire(salaire);
		
		Double salaireD = manager.getSalaire();
		
		//Assertions.assertThat(salaireD).isEqualTo(salaireFinal);
	}

}

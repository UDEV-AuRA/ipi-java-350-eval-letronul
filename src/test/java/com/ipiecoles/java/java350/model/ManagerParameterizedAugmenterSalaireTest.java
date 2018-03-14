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
	
	//Exercice sur la méthode AugmenterSalaire
	
	@Parameter(value = 0)
	public Double salaireBaseManager;
	
	@Parameter(value = 1)
	public Set<Technicien> equipeTest = new HashSet<>();
	
	@Parameter(value = 2)
	public Double salaireFinalManager;
	
	@Parameter(value = 3)
	public Double salaireFinalTechnicien;
	
	@Parameter(value = 4)
	public Double pourcentage;
	
	public static Technicien tech1 = new Technicien("bidon", "d'eau", null, null, 1200.0, null);

	@Parameters(name = "Salaire de base du Manager {0} / Augmentation de {4} /Resultat : Salaire final Manager {2} / Salaire final Technicien {3}")
	public static Collection<Object[]> data() {
		
		//GIVEN
		Set<Technicien> equipe = new HashSet<>();
		equipe.add(tech1);
		equipe.add(new Technicien("bidon", "d'huile", null, null, 1000d, null));
		equipe.add(new Technicien("bidon", "d'essence", null, null, 1000d, null));
		equipe.add(new Technicien("bidon", "de friture", null, null, 1000d, null));
		
		
		Set<Technicien> equipeEmpty = new HashSet<>();

	    return Arrays.asList(new Object[][]{
	           { 1000d, equipe,1100d,null,0.1},
	           { 1000d, equipeEmpty,1200d,null,0.2}
	    	}
	        );
	}
	
	@Test
	public void testEquivalenceSalaire(){
		Manager manager = new Manager("test","test", null, null, salaireBaseManager, null);
		manager.setEquipe(equipeTest);
				
		manager.augmenterSalaire(pourcentage);
		
		Double salaireManagerF = manager.getSalaire();		
		
		Assertions.assertThat(salaireManagerF).isEqualTo(salaireFinalManager);
		Assertions.assertThat(tech1.getSalaire()).isEqualTo(1320.0);
	}

}

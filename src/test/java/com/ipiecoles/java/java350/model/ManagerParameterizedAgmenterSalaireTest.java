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


/*
 * METHODE NON CONFORME 13/03/2013
 * 
 * le logiciel génère des décimaux parasite 
 * lors du lancement de la fonction aumgenterSalaire()
 * 
 * Ex : 3960.0000000000005
 * 
 * Maintenance corrective apporté le 13/03/2013
 * 	- Définir un degré d'arrondissement dans la fonction 
 * 	  pour eviter la génération de ces décimaux
 * 
 */

@RunWith(Parameterized.class)
public class ManagerParameterizedAgmenterSalaireTest {

	@Parameter(value = 0)
	public Double SalaireManager;
	
	@Parameter(value = 1)
	public Double Coeff;
	
	@Parameter(value = 2)
	public boolean equipePleine;
	
	@Parameter(value = 3)
	public Double expectedSalaireManager;
	
	@Parameter(value = 4)
	public Double expectedTotalSalaireTechicien;
	
	
	@Parameters (name = "coeff {0} / technicient {1} / avec une equipe ? {2}! / salaire Manager {3} / salaireTotalTechniciens {4}")
	public static Collection<Object[]> data() {
		//GIVEN
	    return Arrays.asList(new Object[][]{
	           { 1000d, 0d, true, 1800d, 7480d},
	           { 2000d, 0.1d, true, 3960d, 8228d},
	           { 2000d, 0.2d, true, 4320d, 8976d},
	           { 4000d, 0.1d, true, 7920d, 8228.0},
	           { 5000d, 0.1d, false, 7150d, 0d}
	    	}
	        );
	}

	
	@Test
	public void test_ManagerSalaire(){
		//....given
		Manager manager = new Manager();
		manager.setEquipe(generateFullTeam(equipePleine));
		
		//WHEN
		manager.setSalaire(SalaireManager);
		manager.augmenterSalaire(Coeff);
		
		Double salaire = manager.getSalaire();
		
		//THEN
		//System.out.println(salaire);
		Assertions.assertThat(salaire).isEqualTo(expectedSalaireManager);
	}
	
	
	
	@Test
	public void test_TechnicienSalaire(){
		//....given
		Manager manager = new Manager();
		manager.setEquipe(generateFullTeam(equipePleine));
		
		//WHEN
		//manager.setSalaire(SalaireManager);
		manager.augmenterSalaire(Coeff);
		
		
		Double salaireTechicien = 0d;
		
		for(Technicien i : manager.getEquipe()){
			salaireTechicien += i.getSalaire();
			System.out.println(i.getNom() +" : " + i.getSalaire());
		}
		
		System.out.println("totalt : " + salaireTechicien);
		System.out.println("");
		
		//THEN
		Assertions.assertThat(salaireTechicien).isEqualTo(expectedTotalSalaireTechicien);
	}
	
	
	/*
	 * FONCTIONS UTILITAIRES
	 */
	private static Set<Technicien> generateFullTeam(boolean equipePleine){
		
		Set<Technicien> equipeFull = new HashSet<>();
		
		if(equipePleine){
			equipeFull.add(new Technicien("1", null, null, null, 1000d, null));
			equipeFull.add(new Technicien("2", null, null, null, 2000d, null));
			equipeFull.add(new Technicien("3", null, null, null, 3000d, null));
			equipeFull.add(new Technicien("4", null, null, null, 0d, null));
			equipeFull.add(new Technicien());
		}
		
		return equipeFull;
	}
}

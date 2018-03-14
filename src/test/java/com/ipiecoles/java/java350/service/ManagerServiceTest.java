package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Manager;
import com.ipiecoles.java.java350.model.Technicien;
import com.ipiecoles.java.java350.repository.ManagerRepository;
import com.ipiecoles.java.java350.repository.TechnicienRepository;
import com.ipiecoles.java.java350.service.ManagerService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.AdditionalAnswers.returnsFirstArg;


@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {
	
	//Exercice sur la méthode AddTechnicien

    @InjectMocks
    ManagerService managerService;

    @Mock
    ManagerRepository managerRepository;

    @Mock
    TechnicienRepository technicienRepository;

    @Test
    public void testDeleteTechniciens(){
        //Given
        Manager m = new Manager();
        Technicien t = new Technicien();
        m.getEquipe().add(t);
        Mockito.when(managerRepository.findOne(1L)).thenReturn(m);
        Mockito.when(technicienRepository.findOne(2L)).thenReturn(t);

        //When
        managerService.deleteTechniciens(1L,2L);
        //Then
        ArgumentCaptor<Manager> argManager = ArgumentCaptor.forClass(Manager.class);
        Mockito.verify(managerRepository).save(argManager.capture());
        Assertions.assertThat(argManager.getValue().getEquipe()).isEmpty();

        ArgumentCaptor<Technicien> argTechnicien = ArgumentCaptor.forClass(Technicien.class);
        Mockito.verify(technicienRepository).save(argTechnicien.capture());
        Assertions.assertThat(argTechnicien.getValue().getManager()).isNull();
    }

    @Test
    public void testAddTechnicienOK(){
        //Given
        Manager m = new Manager();
        Technicien t = new Technicien();
        Mockito.when(managerRepository.findOneWithEquipeById(1L)).thenReturn(m);
        Mockito.when(managerRepository.save(Mockito.any(Manager.class))).then(returnsFirstArg());
        Mockito.when(technicienRepository.findByMatricule("2L")).thenReturn(t);

        //When
        managerService.addTechniciens(1L,"2L");
        
        //Then
        ArgumentCaptor<Manager> argManager = ArgumentCaptor.forClass(Manager.class);
        Mockito.verify(managerRepository).save(argManager.capture());
        Assertions.assertThat(argManager.getValue().getEquipe().contains(t));

        ArgumentCaptor<Technicien> argTechnicien = ArgumentCaptor.forClass(Technicien.class);
        Mockito.verify(technicienRepository).save(argTechnicien.capture());
        Assertions.assertThat(argTechnicien.getValue().getManager()).isNotNull();
        Assertions.assertThat(argTechnicien.getValue().getManager()).isEqualTo(m);
    }

}
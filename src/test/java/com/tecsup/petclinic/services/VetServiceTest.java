package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;


    @Test
    public void testFindVetById() {
        Integer ID = 1;
        String FIRST_NAME = "Jane";
        String LAST_NAME = "Smith";

        Vet vet = null;

        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info(vet.toString());

        assertEquals(FIRST_NAME, vet.getFirstName());
        assertEquals(LAST_NAME, vet.getLastName());
    }


    @Test
    public void testCreateVet() {
        String FIRST_NAME = "Rebeca";
        String LAST_NAME = "Huallpa";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);

        Vet vetCreated = this.vetService.create(vet);

        log.info("Vet created: {}", vetCreated);

        assertNotNull(vetCreated.getId());
        assertEquals(FIRST_NAME, vetCreated.getFirstName());
        assertEquals(LAST_NAME, vetCreated.getLastName());
    }

    @Test
    public void testUpdateVet() {
        String FIRST_NAME = "John";
        String LAST_NAME = "Doe";
        String UP_FIRST_NAME = "Jane";
        String UP_LAST_NAME = "Smith";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        Vet createdVet = this.vetService.create(vet); // Crear el veterinario primero

        Vet updatedVet = new Vet(createdVet.getId(), UP_FIRST_NAME, UP_LAST_NAME);

        Vet result = this.vetService.update(updatedVet); // Actualizar el veterinario

        log.info("Updated vet: {}", result);

        assertEquals(updatedVet.getFirstName(), result.getFirstName());
        assertEquals(updatedVet.getLastName(), result.getLastName());
    }


    @Test
    public void testDeleteVet() {
        String FIRST_NAME = "John";
        String LAST_NAME = "Doe";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);

        vet = this.vetService.create(vet);
        log.info("{}", vet);

        try {
            this.vetService.delete(vet.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.vetService.findById(vet.getId());
            assertTrue(false);
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }

    // Resto de los métodos de prueba para Vet

}


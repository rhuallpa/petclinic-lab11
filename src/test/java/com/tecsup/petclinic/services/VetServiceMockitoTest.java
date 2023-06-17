package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.util.TObjectCreatorVet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class VetServiceMockitoTest {

    private VetService vetService;

    @Mock 
    private VetRepository repository;

    @BeforeEach
    void setUp() {
        this.vetService = new VetServiceImpl(this.repository);
    }

    /**
     *
     */
    @Test
    public void testFindVetById() {

        Vet vetExpected = TObjectCreatorVet.getVet();

        Mockito.when(this.repository.findById(vetExpected.getId()))
                .thenReturn(Optional.of(vetExpected));
        try {
            vetExpected = this.vetService.findById(vetExpected.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + vetExpected);
        assertEquals(vetExpected.getFirstName(), vetExpected.getFirstName());
        assertEquals(vetExpected.getLastName(), vetExpected.getLastName());

    }

    /**
     *
     */
    @Test
    public void testFindVetByFirstName() {

        String FIND_FIRST_NAME = "John";

        List<Vet> vetsExpected = TObjectCreatorVet.getVetsForFindByFirstName();

        Mockito.when(this.repository.findByFirstName(FIND_FIRST_NAME))
                .thenReturn(vetsExpected);

        List<Vet> vets = this.vetService.findByFirstName(FIND_FIRST_NAME);

        assertEquals(vetsExpected.size(), vets.size());
    }

    /**
     *
     */
    @Test
    public void testFindVetByLastName() {

        String FIND_LAST_NAME = "Doe";

        List<Vet> vetsExpected = TObjectCreatorVet.getVetsForFindByLastName();

        Mockito.when(this.repository.findByLastName(FIND_LAST_NAME))
                .thenReturn(vetsExpected);

        List<Vet> vets = this.vetService.findByLastName(FIND_LAST_NAME);

        assertEquals(vetsExpected.size(), vets.size());
    }

    /**
     * To get ID generate, you need
     * to set up the primary key ID in your
     * entity with the following annotation:
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     */
    @Test
    public void testCreateVet() {

        Vet newVet = TObjectCreatorVet.newVet();
        Vet newCreateVet = TObjectCreatorVet.newVetCreated();

        Mockito.when(this.repository.save(newVet))
                .thenReturn(newCreateVet);

        Vet vetCreated = this.vetService.create(newVet);

        log.info("Vet created: {}", vetCreated);

        assertNotNull(vetCreated.getId());
        assertEquals(newCreateVet.getFirstName(), vetCreated.getFirstName());
        assertEquals(newCreateVet.getLastName(), vetCreated.getLastName());

    }

    /**
     *
     */
    @Test
    public void testUpdateVet() {

        String UP_VET_FIRST_NAME = "Alice";
        String UP_VET_LAST_NAME= "Smith";
        Vet newVet = TObjectCreatorVet.newVetForUpdate();
        Vet newVetCreate = TObjectCreatorVet.newVetCreatedForUpdate();

        // ------------ Create ---------------

        Mockito.when(this.repository.save(newVet))
                .thenReturn(newVetCreate);

        Vet vetCreated = this.vetService.create(newVet);
        log.info("{}", vetCreated);

        // ------------ Update ---------------

        // Prepare data for update
        vetCreated.setFirstName(UP_VET_FIRST_NAME);
        vetCreated.setLastName(UP_VET_LAST_NAME);

        Vet newUpdate = vetCreated;

        // Create
        Mockito.when(this.repository.save(vetCreated))
                .thenReturn(newUpdate);

        // Execute update
        Vet upgradedVet = this.vetService.update(vetCreated);
        log.info("{}", upgradedVet);

        assertEquals(UP_VET_FIRST_NAME, upgradedVet.getFirstName());
        assertEquals(UP_VET_LAST_NAME, upgradedVet.getLastName());
    }

    /**
     *
     */
    @Test
    public void testDeleteVet() {

        Vet newVet = TObjectCreatorVet.newVetForDelete();
        Vet newVetCreate = TObjectCreatorVet.newVetCreatedForDelete();

        // ------------ Create ---------------

        Mockito.when(this.repository.save(newVet))
                .thenReturn(newVetCreate);

        Vet vetCreated = this.vetService.create(newVet);
        log.info("{}", vetCreated);

        // ------------ Delete ---------------

        Mockito.doNothing().when(this.repository).delete(newVetCreate);
        Mockito.when(this.repository.findById(newVetCreate.getId()))
                .thenReturn(Optional.of(newVetCreate));

        try {
            this.vetService.delete(vetCreated.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validate ---------------

        Mockito.when(this.repository.findById(newVetCreate.getId()))
                .thenReturn(Optional.ofNullable(null));

        try {
            this.vetService.findById(vetCreated.getId());
            assertTrue(false);
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }
}

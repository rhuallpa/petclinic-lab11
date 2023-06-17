package com.tecsup.petclinic.util;

import com.tecsup.petclinic.entities.Vet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreatorVet {

    public static Vet getVet() {
        return new Vet(1, "James", "Carter");
    }

    public static Vet newVet() {
        return new Vet("John", "Doe");
    }

    public static Vet newVetCreated() {
        Vet vet = new Vet("John", "Doe");
        vet.setId(1000);
        return vet;
    }

    public static Vet newVetForUpdate() {
        return new Vet("Jane", "Smith");
    }

    public static Vet newVetCreatedForUpdate() {
        Vet vet = new Vet("Jane", "Smith");
        vet.setId(4000);
        return vet;
    }

    public static Vet newVetForDelete() {
        return new Vet("Michael", "Johnson");
    }

    public static Vet newVetCreatedForDelete() {
        Vet vet = new Vet("Michael", "Johnson");
        vet.setId(2000);
        return vet;
    }

    public static List<Vet> getVetsForFindByName() {
        List<Vet> vets = new ArrayList<>();
        vets.add(new Vet(1, "James", "Carter"));
        return vets;
    }

    public static List<Vet> getVetsForFindBySpecialtyId() {
        List<Vet> vets = new ArrayList<>();
        vets.add(new Vet(2, "Helen", "Leary"));
        vets.add(new Vet(3, "Linda", "Douglas"));
        return vets;
    }

    public static List<Vet> getVetsForFindByFirstName() {
        List<Vet> vets = new ArrayList<>();
        vets.add(new Vet(4, "John", "Doe"));
        vets.add(new Vet(6, "Sharon", "Jenkins")); // Additional data from database
        return vets;
    }

    public static List<Vet> getVetsForFindByLastName() {
        List<Vet> vets = new ArrayList<>();
        vets.add(new Vet(5, "Jane", "Smith"));
        return vets;
    }
}

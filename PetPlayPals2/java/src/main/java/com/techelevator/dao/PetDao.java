package com.techelevator.dao;

import com.techelevator.model.Pet;

import java.util.List;

public interface PetDao {

    public List<Pet> getAllPets();
    public List<Pet> getPetsByUserId(int userId);
    public List<Pet> getPetsBySpecies(String petSpecies);
    public List<Pet> getPetsByBreed(String petBreed);
    public List<Pet> getPetsByPlaydateId(int playdateId);

    public Pet getHostPet(int playdateId);

    public Pet getPetById(int petId);
    public Pet addPet(Pet petSave);
    public void removePetById(int petId);
    public Pet updatePet(int petId, Pet petUpdate);

}

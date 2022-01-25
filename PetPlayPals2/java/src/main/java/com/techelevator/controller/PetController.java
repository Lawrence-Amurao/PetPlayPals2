package com.techelevator.controller;

import com.techelevator.dao.PetDao;
import com.techelevator.exception.PetNotFoundException;
import com.techelevator.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PetController {

    @Autowired
    private PetDao petDao;

    // Create Methods
    @RequestMapping (path = "/pets/add", method = RequestMethod.POST)
    public Pet addNewPet(@RequestBody Pet pet)
            throws PetNotFoundException{
        return petDao.addPet(pet);
    }

    // Retrieve Methods
    @RequestMapping (path = "/pets", method = RequestMethod.GET)
    public List<Pet> getAllPets() {
        return petDao.getAllPets();
    }

    @RequestMapping (path = "/pets/users/{userId}", method = RequestMethod.GET)
    public List<Pet> getPetsByUserId(@PathVariable("userId") int userId) {
        return petDao.getPetsByUserId(userId);
    }

    @RequestMapping (path = "/pets/species/{species}", method = RequestMethod.GET)
    public List<Pet> getPetsBySpecies(@PathVariable("species") String species) {
        return petDao.getPetsBySpecies(species);
    }

    @RequestMapping (path = "/pets/breed/{breed}", method = RequestMethod.GET)
    public List<Pet> getPetsByBreed(@PathVariable("breed") String breed) {
        return petDao.getPetsByBreed(breed);
    }

    @RequestMapping (path = "/pets/playdate/{id}", method = RequestMethod.GET)
    public List<Pet> getPetsByPlaydate(@PathVariable("id") int playdateId) {
        return petDao.getPetsByPlaydateId(playdateId);
    }

    @RequestMapping (path = "/pets/{id}", method = RequestMethod.GET)
    public Pet getPetById(@PathVariable("id") int petId)
            throws PetNotFoundException {
        return petDao.getPetById(petId);
    }

    // Update Methods
    @RequestMapping (path = "/pets/{id}/update", method = RequestMethod.PUT)
    public Pet updatePet(@RequestBody Pet pet, @PathVariable("id") int petId)
            throws PetNotFoundException {
        return petDao.updatePet(petId, pet);
    }

    // Delete Methods
    @RequestMapping (path = "/pets/{id}/remove", method = RequestMethod.DELETE)
    public void removePet(@PathVariable("id") int petId)
            throws PetNotFoundException {
        petDao.removePetById(petId);
    }
}

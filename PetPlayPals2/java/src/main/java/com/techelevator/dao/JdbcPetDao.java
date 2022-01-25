package com.techelevator.dao;

import com.techelevator.model.Pet;
import com.techelevator.model.PlayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPetDao implements PetDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    public JdbcPetDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes FROM pet;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Pet pet = mapRowToPet(results);
            pets.add(pet);
        }
        return pets;
    }

    @Override
    public List<Pet> getPetsByUserId(int userId) {
        List<Pet> userPets = new ArrayList<>();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes FROM pet " +
                "JOIN users ON owner_id = user_id " +
                "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Pet pet = mapRowToPet(results);
            userPets.add(pet);
        }
        return userPets;
    }

    @Override
    public List<Pet> getPetsBySpecies(String petSpecies) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes " +
                "FROM pet WHERE pet_type = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petSpecies);
        while (results.next()) {
            Pet pet = mapRowToPet(results);
            pets.add(pet);
        }
        return pets;
    }

    @Override
    public List<Pet> getPetsByBreed(String petBreed) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes " +
                "FROM pet WHERE breed = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petBreed);
        while (results.next()) {
            Pet pet = mapRowToPet(results);
            pets.add(pet);
        }
        return pets;
    }

    @Override
    public List<Pet> getPetsByPlaydateId(int playdateId) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes " +
                "FROM pet WHERE pet_id IN (SELECT pet_id FROM playdate_attendee WHERE playdate_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playdateId);
        while (results.next()) {
            Pet pet = mapRowToPet(results);
            pets.add(pet);
        }
        return pets;
    }

    @Override
    public Pet getPetById(int petId) {
        Pet pet = new Pet();
        String sql = "SELECT id, pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes " +
                "FROM pet WHERE id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petId);
        if (results.next()) {
            pet = mapRowToPet(results);
        }
        return pet;
    }

    @Override
    public Pet addPet(Pet petSave) {
        String sql = "INSERT INTO pet (pet_name, pet_type, owner_id, breed, size, energy_level, age, pet_description, likes, dislikes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, petSave.getPetName(), petSave.getPetSpecies(), petSave.getOwnerId(), petSave.getPetBreed(),
                petSave.getPetSize(), petSave.getPetActivityLevel(), petSave.getPetAgeGroup(), petSave.getPetDescription(), petSave.getPetLikes(),
                petSave.getPetDislikes());

        return getPetById(newId);
    }

    @Override
    public void removePetById(int petId) {
        String sql = "DELETE FROM playdate_attendee WHERE pet_id = ?;";
        jdbcTemplate.update(sql, petId);
        sql = "DELETE FROM pet WHERE id = ?;";
        jdbcTemplate.update(sql, petId);
    }

    @Override
    public Pet updatePet(int petId, Pet petUpdate) {
        String sql = "UPDATE pet " +
                "SET pet_name = ?, pet_type = ?, owner_id = ?, breed = ?, size = ?, energy_level = ?, age = ?, pet_description = ?, " +
                "likes = ?, dislikes = ? WHERE id = ?;";
        jdbcTemplate.update(sql, petUpdate.getPetName(), petUpdate.getPetSpecies(), petUpdate.getOwnerId(), petUpdate.getPetBreed(),
                petUpdate.getPetSize(), petUpdate.getPetActivityLevel(), petUpdate.getPetAgeGroup(), petUpdate.getPetDescription(), petUpdate.getPetLikes(),
                petUpdate.getPetDislikes(), petId);
        return getPetById(petId);
    }

    @Override
    public Pet getHostPet(int playdateId) {
        Pet hostPet = new Pet();
        String sql = "SELECT pet_id FROM playdate_attendee WHERE playdate_id = ? AND request_status = 'Main';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playdateId);
        if (results.next()) {
            hostPet = getPetById(results.getInt("pet_id"));
        }
        return hostPet;
    }

    private Pet mapRowToPet(SqlRowSet result) {
        Pet pet = new Pet();
        pet.setOwnerId(result.getInt("owner_id"));
        pet.setPetId(result.getInt("id"));
        pet.setOwnerUsername(userDao.getUserById((long) pet.getOwnerId()).getUsername());
        pet.setPetName(result.getString("pet_name"));
        pet.setPetSpecies(result.getString("pet_type"));
        pet.setPetBreed(result.getString("breed"));
        pet.setPetAgeGroup(result.getString("age"));
        pet.setPetActivityLevel(result.getString("energy_level"));
        pet.setPetDescription(result.getString("pet_description"));
        pet.setPetSize(result.getString("size"));
        pet.setPetLikes(result.getString("likes"));
        pet.setPetDislikes(result.getString("dislikes"));
        return pet;
    }

}

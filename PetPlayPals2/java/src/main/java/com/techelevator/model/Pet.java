package com.techelevator.model;

public class Pet {

    private int petId;
    private String petName;
    private String petSpecies;
    private String ownerUsername;

    private String petBreed;
    private String petAgeGroup;
    private String petSize;

    private String petActivityLevel;

    private String petDescription;
    private String petLikes;
    private String petDislikes;

    private int ownerId;

    public Pet() {

    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetAgeGroup() {
        return petAgeGroup;
    }

    public void setPetAgeGroup(String petAgeGroup) {
        this.petAgeGroup = petAgeGroup;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }

    public String getPetActivityLevel() {
        return petActivityLevel;
    }

    public void setPetActivityLevel(String petActivityLevel) {
        this.petActivityLevel = petActivityLevel;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetLikes() {
        return petLikes;
    }

    public void setPetLikes(String petLikes) {
        this.petLikes = petLikes;
    }

    public String getPetDislikes() {
        return petDislikes;
    }

    public void setPetDislikes(String petDislikes) {
        this.petDislikes = petDislikes;
    }
}

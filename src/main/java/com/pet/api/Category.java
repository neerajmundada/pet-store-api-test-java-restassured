package com.pet.api;

/**
 * This class represents a Category object used in the pet store API.
 * A Category object contains attributes such as ID and name.
 * Categories are used to classify and group pet objects based on their types or breeds.
 * The class provides getter and setter methods to access and modify these attributes.
 */
public class Category {
    private long id; // ID of the category
    private String name; // Name of the category


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
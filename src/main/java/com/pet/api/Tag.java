package com.pet.api;

/**
 * This class represents a Tag object used in the pet store API.
 * A Tag object contains attributes such as ID and name.
 * Tags are used to categorize and add additional information to pet objects.
 * The class provides getter and setter methods to access and modify these attributes.
 */
public class Tag {
    private long id; // ID of the tag
    private String name; // Name of the tag

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
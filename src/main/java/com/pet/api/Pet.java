package com.pet.api;

import java.util.List;

/**
 * This class represents a Pet object used in the pet store API.
 * A Pet object includes attributes such as name, photo URLs, ID, category, tags, and status.
 * It provides getter and setter methods to access and modify these attributes.
 */
public class Pet {
    private String name; // Name of the pet
    private List<String> photoUrls; // List of photo URLs for the pet
    private long id; // ID of the pet
    private Category category; // Category of the pet (e.g., dog, cat)
    private List<Tag> tags; // List of tags associated with the pet
    private String status; // Status of the pet (e.g., available, sold, pending)


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


package com.store.api;

/**
 * This class represents an Order object used in the store API.
 * It contains attributes related to an order, such as ID, pet ID, quantity, ship date, status, and completion status.
 * The class provides getter and setter methods to access and modify the attributes.
 */

public class Order {
    private long id;    // Order ID

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    private long petId;     // Pet ID related to the order

    private int quantity;   // Quantity of the ordered item

    private String shipDate;    // Date for shipping the order

    private String status; // Status of the order

    private boolean complete;   // Completion status of the order


}

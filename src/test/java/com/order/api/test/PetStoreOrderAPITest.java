package com.order.api.test;

import com.pet.api.Category;
import com.pet.api.Pet;
import com.pet.api.Tag;
import com.store.api.Order;
import common.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreOrderAPITest extends BaseClass {

    @Test
    public void testPlaceOrder() {
        Long petID;
        Long orderID;


        // Send a GET request to find a pet with the specified status (available)
        // Verify the response code and content type
        RequestSpecification request = given()
                .header("Accept", "application/json");

        ValidatableResponse getPetResponse = request
                .when()
                .get(BASE_URL + "/pet/findByStatus?status=" + AVAIABLE_STATUS)
                .then()
                .statusCode(200)
                .contentType("application/json");

        // Log the response code and response body in report
        reportLog("Response Code : " + getPetResponse.extract().statusCode());
        reportLog("Response : " + getPetResponse.extract().body().asPrettyString());

        // Check if the pet with the specific name and category exists in the response
        // If not, create a new pet with a random ID and add it to the pet store
        if (getPetResponse.extract().jsonPath().getString("name=='pupo' && category.name == 'pajaro'").toString().equals("false")) {
            petID = System.currentTimeMillis();

            // Create the new pet object and set its attributes
            Pet newPet = new Pet();
            newPet.setName("pupo");
            newPet.setPhotoUrls(Arrays.asList("https://example.com/fluffy1.jpg", "https://example.com/fluffy2.jpg"));
            newPet.setId(petID);

            Category category = new Category();
            category.setId(1);
            category.setName("pajaro");
            newPet.setCategory(category);

            Tag friendlyTag = new Tag();
            friendlyTag.setId(101);
            friendlyTag.setName("pupodog");

            Tag smallTag = new Tag();
            smallTag.setId(102);
            smallTag.setName("pupodog");

            newPet.setTags(Arrays.asList(friendlyTag, smallTag));
            newPet.setStatus("available");

            JSONObject petJsonObject = new JSONObject(newPet);

            // Send a POST request to add the new pet to the pet store
            RequestSpecification addPetRequest = given()
                    .header("Content-Type", "application/json")
                    .body(newPet);

            // Verify the response code and the attributes of the added pet
            ValidatableResponse addPetResponse = addPetRequest
                    .when()
                    .post(BASE_URL + "/pet")
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("name", equalTo("pupo"))
                    .body("status", equalTo("available"))
                    .body("category.name", equalTo("pajaro"));

            // Log the response code, request body, and response body in report
            reportLog("Response Code : " + addPetResponse.extract().statusCode());
            reportLog("Request : " + petJsonObject);
            reportLog("Response : " + addPetResponse.extract().asPrettyString());
            reportLog("Name : " + addPetResponse.extract().jsonPath().getString("name").toString());
            reportLog("Status : " + addPetResponse.extract().jsonPath().getString("status").toString());
            reportLog("Category : " + addPetResponse.extract().jsonPath().getString("category.name").toString());
            petID = Long.parseLong(addPetResponse.extract().jsonPath().getString("id"));
            reportLog("ID : " + petID);

        } else {
            // If the pet already exists, retrieve its ID from the response
            petID = Long.parseLong(getPetResponse.extract().jsonPath().get("name=='pupo' && category.name == 'pajaro'").toString());

        }

        // Create a random order ID and set up the order object
        orderID = System.currentTimeMillis();

        Order order = new Order();
        order.setId(orderID);
        order.setPetId(petID);
        order.setQuantity(1);
        order.setComplete(true);

        JSONObject orderJsonObject = new JSONObject(order);

        // Send a POST request to place the order
        RequestSpecification orderReqeust = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(order);

        // Verify the response code and the "complete" attribute of the order
        ValidatableResponse orderPetResponse = orderReqeust
                .when()
                .post(BASE_URL + "/store/order")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("complete", equalTo(true));

        // Log the response code, request body, and response body in report
        reportLog("Response Code : " + orderPetResponse.extract().statusCode());
        reportLog("Request : " + orderJsonObject);
        reportLog("Response : " + orderPetResponse.extract().asPrettyString());

    }

    @Test
    public void testUpdatePet() {
        Long petID;
        String petName, categoryName, tagName;
        ArrayList<String> petList = new ArrayList<String>();

        petName = "kurikuri";
        categoryName = "pomeranian";
        tagName = "Super Cute";

        Pet pet = new Pet();

        // Send a GET request to find a pet with the specified status (available)
        RequestSpecification request = given()
                .header("Accept", "application/json");

        // Verify the response code and content type
        ValidatableResponse getPetResponse = request
                .when()
                .get(BASE_URL + "/pet/findByStatus?status=" + AVAIABLE_STATUS)
                .then()
                .statusCode(200)
                .contentType("application/json");

        // Log the response code and response body in report
        reportLog("Response Code : " + getPetResponse.extract().statusCode());
        reportLog("Response : " + getPetResponse.extract().body().asPrettyString());

        // Check if the pet with the specific name and category exists in the response
        // If not, create a new pet with a random ID and add it to the pet store
        if (getPetResponse.extract().jsonPath().getString("name=='kurikuri' && category.name == 'pomeranian'").toString().equals("false")) {

            petID = System.currentTimeMillis();

            // Create the new pet object and set its attributes
            pet = new Pet();
            pet.setName(petName);
            pet.setPhotoUrls(Arrays.asList("https://example.com/fluffy1.jpg", "https://example.com/fluffy2.jpg"));
            pet.setId(petID);

            Category category = new Category();
            category.setId(1);
            category.setName(categoryName);
            pet.setCategory(category);

            Tag friendlyTag = new Tag();
            friendlyTag.setId(101);
            friendlyTag.setName(tagName);

            pet.setTags(Arrays.asList(friendlyTag));
            pet.setStatus("available");

            JSONObject petJsonObject = new JSONObject(pet);

            // Send a POST request to add the new pet to the pet store
            RequestSpecification addPetRequest = given()
                    .header("Content-Type", "application/json")
                    .body(pet);

            // Verify the response code and the attributes of the added pet
            ValidatableResponse addPetResponse = addPetRequest
                    .when()
                    .post(BASE_URL + "/pet")
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("id", equalTo(petID));

            // Log the response code, request body, and response body in report
            reportLog("Response Code : " + addPetResponse.extract().statusCode());
            reportLog("Request : " + petJsonObject);
            reportLog("Response : " + addPetResponse.extract().asPrettyString());
            reportLog("Name : " + addPetResponse.extract().jsonPath().getString("name").toString());
            reportLog("Status : " + addPetResponse.extract().jsonPath().getString("status").toString());
            reportLog("Category : " + addPetResponse.extract().jsonPath().getString("category.name").toString());
            petID = Long.parseLong(addPetResponse.extract().jsonPath().getString("id"));
            reportLog("ID : " + petID);

            petList.add(addPetResponse.extract().jsonPath().get("id").toString());

        } else {
            // If the pet already exists, retrieve its ID from the response
            petList = getPetResponse.extract().jsonPath().get("findAll() {e -> e.name=='kurikuri' && e.category.name == 'pomeranian'}.id");

            pet.setName(petName);
            pet.setPhotoUrls(Arrays.asList("https://example.com/fluffy1.jpg", "https://example.com/fluffy2.jpg"));


            Category category = new Category();
            category.setId(1);
            category.setName(categoryName);
            pet.setCategory(category);

            Tag friendlyTag = new Tag();

            friendlyTag.setId(101);
            friendlyTag.setName(tagName);
            pet.setTags(Arrays.asList(friendlyTag));

            pet.setStatus("available");

        }

        for (String pID : petList) {

            pet.setId(Long.parseLong(pID));

            JSONObject petJsonObject = new JSONObject(pet);

            // Send a POST request to update the pet
            RequestSpecification updatePetRequest = given()
                    .header("Content-Type", "application/json")
                    .body(pet);
            // Verify the response code, name and id
            ValidatableResponse updatePetResponse = updatePetRequest
                    .when()
                    .put(BASE_URL + "/pet")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("name", equalTo(petName))
                    .body("id", equalTo(Long.parseLong(pID)));

            // Log the response code, request body, response body and tag name in report
            reportLog("Response Code : " + updatePetResponse.extract().statusCode());
            reportLog("Request : " + petJsonObject);
            reportLog("Response : " + updatePetResponse.extract().asPrettyString());
            reportLog("Tag Name : " + updatePetResponse.extract().jsonPath().getString("tags.name").toString());

        }


    }
}

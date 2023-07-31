package com.pet.api.test;


import com.pet.api.Category;
import com.pet.api.Pet;
import com.pet.api.Tag;
import common.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;


public class PetStoreAPITest extends BaseClass {
    private String dependentPetId = "4";

    @Test
    public void findAvailablePetByStatus() {

        // Send a GET request to find pets with the specified status (available).
        RequestSpecification request = given()
                .header("Accept", "application/json");

        // Verify the response code and content type.
        ValidatableResponse response = request
                .when()
                .get(BASE_URL + "/pet/findByStatus?status=" + AVAIABLE_STATUS)
                .then()
                .statusCode(200)
                .contentType("application/json");


        // Log the response code and response body in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());

        // Extract the ID of a pet with name 'fish' from the response and store it in dependentPetId to be used for other tests dependency
        dependentPetId = response.extract().jsonPath().getList("findAll() {e-> e.name=='fish'}.id").get(0).toString();
    }

    @Test
    public void testCreatePet() {
        // Create a new pet object and set its attributes
        Pet newPet = new Pet();
        newPet.setName("Fluffy");
        newPet.setPhotoUrls(Arrays.asList("https://example.com/fluffy1.jpg", "https://example.com/fluffy2.jpg"));
        newPet.setId(12345);

        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        newPet.setCategory(category);

        Tag friendlyTag = new Tag();
        friendlyTag.setId(101);
        friendlyTag.setName("friendly");

        Tag smallTag = new Tag();
        smallTag.setId(102);
        smallTag.setName("small");

        newPet.setTags(Arrays.asList(friendlyTag, smallTag));
        newPet.setStatus("available");

        JSONObject jsonObject = new JSONObject(newPet);

        // Send a POST request to add the new pet to the pet store
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(newPet);

        // Verify the response code and the attributes of the added pet using matchers
        ValidatableResponse response = request
                .when()
                .post(BASE_URL + "/pet")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", equalTo("Fluffy"))
                .body("status", equalTo("available"))
                .body("category.name", equalTo("Dogs"))
                .body("tags.name", hasItems("friendly", "small"));


        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Request : " + jsonObject);
        reportLog("Response : " + response.extract().asPrettyString());
        reportLog("Name : " + response.extract().jsonPath().getString("name").toString());
        reportLog("Status : " + response.extract().jsonPath().getString("status").toString());
        reportLog("Category : " + response.extract().jsonPath().getString("category.name").toString());
        reportLog("Tags : " + response.extract().jsonPath().getString("tags.name").toString());

    }


    @Test
    public void testFindPetsByInvalidStatus() {
        // Send a GET request to find pets with an invalid status
        RequestSpecification request = given()
                .queryParam("status", "invalid_status")
                .header("Accept", "application/json");

        // Verify that the response body is empty using matchers.
        ValidatableResponse response = request
                .when()
                .get(BASE_URL + "/pet/findByStatus")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isEmpty()", Matchers.is(true));

        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());
    }


    @Test
    public void testInvalidContentType() {
        // Send a GET request to find pets with an invalid status.
        RequestSpecification request = given()
                .queryParam("status", "available")
                .header("Accept", "application/xml");

        // Verify that the response body is empty using matchers.
        ValidatableResponse response = request
                .when()
                .get(BASE_URL + "/pet/findByStatus?status=dummy")
                .then()
                .statusCode(200)
                .body("isEmpty()", Matchers.is(true));

        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());
    }

    @Test
    public void testInvalidEndpoint() {
        // Send a GET request to find pets with an invalid status.
        RequestSpecification request = given()
                .queryParam("status", "available")
                .header("Accept", "application/json");

        // Verify that the response body is empty using matchers.
        ValidatableResponse response = request
                .when()
                .get(BASE_URL + "/pet/findByStatus_invalid")
                .then()
                .statusCode(404);

        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());
    }

    @Test(dependsOnMethods = "findAvailablePetByStatus")
    public void testFindPetByID() {
        // Send a GET request to find pets with an invalid status.
        RequestSpecification request = given()
                .header("Accept", "application/json");

        // Verify that the response body is empty using matchers.
        ValidatableResponse response = request
                .when()
                .get(BASE_URL + "/pet/" + dependentPetId)
                .then()
                .statusCode(200);

        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());
    }

    @Test(dependsOnMethods = "testFindPetByID")
    public void testDeletePet() {
        // Send a GET request to find pets with an invalid status.
        RequestSpecification request = given()
                .header("Accept", "application/json");

        // Verify that the response body is empty using matchers.
        ValidatableResponse response = request
                .when()
                .delete(BASE_URL + "/pet/" + dependentPetId)
                .then()
                .statusCode(200);

        // Log the details in report
        reportLog("Response Code : " + response.extract().statusCode());
        reportLog("Response : " + response.extract().body().asPrettyString());
    }
}

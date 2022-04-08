package project;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GitHub_RestAssured_Project {

	RequestSpecification reqSpec;

	int id;

	@BeforeClass
	public void setUp() {
		reqSpec = new RequestSpecBuilder()
				// Set Content type
				.setContentType(ContentType.JSON)
				// Set Authorization header
				.addHeader("Authorization", "token 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" )
				// set base url
				.setBaseUri("https://api.github.com").build();
		
		}

	@Test(priority = 1)
	public void addToken() {

		// Create JSON request

		String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDpuxwuz7ExKj9WnFWr8ocRl6+QtmVmvvPUnShrAOUZ8x3ftMTTbksjBMa4J3pm5YgeOWHAhX3X+H4Hc0uoaoiPOtZVd9PaDiaBe4EKWLXG9WoyX+au3hK8CpQGib0Ljp9zqyzvTdppRmZbiQA/6eCWE5y5awWwa8eKZ/HwVnf+V9jhrNjaXMohxbyBH59d+JYH9tsoEtv/ZMm5H6os4c5EiHDSqi1yeRYbKTiNW30DTE+o6xdU18B6/2WOTDZxCbfZhhBdMgFtdj25dNpGjTpvapjvs9+xanqwMPayCGY8F95YxJ7nWmGSZTl0ZzbkqjAwrlKGsq5x3iUTUMq6urCwl+xJkn82Uk/VK71Q/XFaS9jXfWMK3iT7SLrsZ/gQ/4FkVczizzREuMMZ6lY6SMyL1SLXkAuVY0bNnCXgeqBlAIUyWURb4qij0e4ZkkAvhBjuNyV2AZweE69dRnaoAfs7SY01+rSRArTbv+wJPRDAXI+zJeBlvdNSa+io/f+BGfs=\"}";
		Response response = given().spec(reqSpec)// Set headers
				.body(reqBody) // Add request body
				.when().post("/user/keys"); // Send POST request
		
		// Print response
				String body = response.getBody().asPrettyString();
				System.out.println(body);
			
		//Extract name from response
		id = response.then().extract().path("id");
		System.out.println("Response id:" + id);

		// Assertion
         	response.then().statusCode(201);
	
		

	}

	@Test(priority = 2)
	public void gettoken() {
		Response response = given().spec(reqSpec) // Set headers
				.when()
				.pathParam("key_id",id)
				.get("/user/keys/{key_id}"); // Send GET request

		
		
		// Print response
		String body = response.getBody().asPrettyString();
		System.out.println(body);
		

		// Assertion
	     response.then().statusCode(200);
    

	}

	@Test(priority = 3,enabled= false)
	public void deleteToken() {
		Response response = given().spec(reqSpec) // Set headers
				.when()
				.pathParam("key_id",id)
				// Set path parameter
				.delete("/user/keys/{key_id}"); // Send DELETE request Reporter.log(sshkey);
        Reporter.log(response.asString());
		
		// Assertion
		response.then().statusCode(204);
       

	}

}


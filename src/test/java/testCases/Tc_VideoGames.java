package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

public class Tc_VideoGames {

	@Test(priority = 1)
	public void test_GetAllVideoGames() {

		given()
		
		.when()
				.get("http://localhost:8080/app/videogames")
		.then()
				.statusCode(200);
	}

	@Test(priority = 2)
	public void test_addnewVideoGame() {

		HashMap data = new HashMap();

		data.put("id", "100");
		data.put("name", "Spider-man");
		data.put("releaseDate", "2022-08-22T05:55");
		data.put("reviewScore", "5");
		data.put("Category", "Adventure");
		data.put("rating", "Universal");

		Response resp = 
				given()
						.contentType("application/json")
						.body(data)
				.when()
						.post("http://localhost:8080/app/videogames")
				.then()
						.statusCode(200)
						.log().body()
						.extract().response();

		String jsonString = resp.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);

	}
	@Test(priority = 3)
	public void test_getVideoGame() {
		
		given()
		
		.when()
				.get("http://localhost:8080/app/videogames/100")
		.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id", equalTo("100"))
				.body("videoGame.name", equalTo("Spider-man"));
	}
	
	@Test(priority = 4)
	public void test_updatevideogame() {
		
		HashMap data = new HashMap();

		data.put("id", "100");
		data.put("name", "Bat-man");
		data.put("releaseDate", "2022-08-22T05:55");
		data.put("reviewScore", "4");
		data.put("Category", "Adventure");
		data.put("rating", "Universal");
		
		given()
				.contentType("application/json")
				.body(data)
		.when()
				.put("http://localhost:8080/app/videogames/100")
		.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id", equalTo("100"))
				.body("videoGame.name", equalTo("Bat-man"));
	}
	
	@Test(priority = 5)
	public void test_deleteVideogame() throws InterruptedException {
		
		Response resp = 
		given()
		
		.when()
				.delete("http://localhost:8080/app/videogames/100")
		.then()
				.statusCode(200)
				.log().body()
				.extract().response();
		
		Thread.sleep(3000);
		String jsonString = resp.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}
}

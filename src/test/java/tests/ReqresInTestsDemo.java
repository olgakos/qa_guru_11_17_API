package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static io.restassured.RestAssured.*;



public class ReqresInTestsDemo {

   // @BeforeAll
    //static void setUp() {
      //  RestAssured.baseURI = "https://reqres.in/";
    //}


    @Test
    void successfulLogin() {
        /*
        request: https://reqres.in/api/login
        data:
        {
            "email": "eve.holt@reqres.in",
            "password": "cityslicka"
        }
        response:
        {
            "token": "QpwL5tke4Pnpja7X4"
        }
         */

        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";

        given()
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void missingPasswordLogin() {
        /*
        request: https://reqres.in/api/login
        data:
        {
            "email": "eve.holt@reqres.in"
        }
        response:
        {
            "error": "Missing password"
        }
         */

        String authorizedData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login") //post!
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }


    // еще несколкьо коротких демо-тестов к этому сайту:

    @Test
    void singlUserTest(){
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .body("data.first_name", is("Janet"));
    }

    String inputDataCreate = "{\"name\": \"morpheus\", " +
            "\"job\": \"leader\"}";
    @Test
    void createTest(){
        given()
                .body(inputDataCreate)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .body("name", is("morpheus"));
    }


    String inputDataRegister = "{\"email\": \"eve.holt@reqres.in\", " +
            "\"password\": \"pistol\"}";
    @Test
    void registerSuccessfulTest(){
        given()
                .body(inputDataRegister)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    String inputDataUpdate = "{\"name\": \"morpheus\", " +
            "\"job\": \"zion resident\"}";

    @Test
    void updateTest(){
        given()
                .body(inputDataUpdate)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users")
                .then()
                .body("job", is("zion resident"));
    }


    @Test
    void deleteTest(){
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

}
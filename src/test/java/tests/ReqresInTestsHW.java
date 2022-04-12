package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTestsHW {

//---1
    @Test
    void registerNewUserTest200() {
        /*
        request: https://reqres.in/api/register

        data:
        {
        "email": "eve.holt@reqres.in",
        "password": "pistol"
        }

        response:
        {
        "id": 4,
        "token": "QpwL5tke4Pnpja7X4"
        }
         */

        String registerData = "{\"email\": \"eve.holt@reqres.in\",\n" +
                "\"password\": \"pistol\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

//---2

    String data = "{\"name\": \"morpheus2\", \"job\": \"zion resident1\"}";

    @Test
    void updateUserTest() {
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users")//put
                .then()
                .body("name", is("morpheus2"))
                .body("job", is("zion resident1"));
    }

//---2b
    @Test
    void updateUserTest2() {
        /*
request: https://reqres.in/api/users/2

data:
        {
    "name": "morpheus",
    "job": "zion resident"
}

response:
        {
    "name": "morpheus",
    "job": "zion resident",
    "updatedAt": "2022-04-12T11:51:24.860Z"
}
         */

        String updData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

        given()
                .body(updData)
                .param("name", "morpheus")
                .param("job", "zion resident")
                .param("updatedAt", "2022-04-12T11:51:24.860Z")
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/2") //put!
                .then()
                .statusCode(200)
                //.body("updatedAt", is("2022-04-12T11:51:24.860Z"));
                .body("name", is("morpheus"));
    }

//----3

    @Test
    void deleteUserTest204a() {
        String response = delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .extract().response().asString();
        System.out.println(response);
    }

//----3b
    @Test
    void deleteUserTest204b() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

//----4
    @Test //get SINGLE USER NOT FOUND
    void singleUserNotFound404() {
          /*
        request: https://reqres.in/api/users/23
        response: 404
        response: {}
     */
        String response =
                get("https://reqres.in/api/users/23")
                        .then()
                        .statusCode(404)
                        .extract().response().asString();

        System.out.println(response);
    }

//----5

    @Test
    void delayedResponse1() {
        String response =
                get("https://reqres.in/api/users?delay=3")
                        .then()
                        .statusCode(200)
                        .extract().response().asString();
        System.out.println(response);
    }


//----6

    @Test //get SINGLE <RESOURCE>
    void singleResource200unknown2() {
        /*
        request: https://reqres.in/api/unknown/2
        response: 200
        response:
            {
    "data": {
        "id": 2,
        "name": "fuchsia rose",
        "year": 2001,
        "color": "#C74375",
        "pantone_value": "17-2031"
    },
    "support": {
        "url": "https://reqres.in/#support-heading",
        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
    }
}
         */

        String data = "{\"id\": 2,\n" +
                "        \"name\": \"fuchsia rose\",\n" +
                "        \"year\": 2001,\n" +
                "        \"color\": \"#C74375\",\n" +
                "        \"pantone_value\": \"17-2031\"}";

        given()
                .contentType(JSON)
                //.body(data)
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"))
                .body("data.year", is(2001))
                .body("data.color", is("#C74375"))
                .body("data.pantone_value", is("17-2031"))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }


//====7?  падает на "createdAt"

    @Test
    /*
    data:
    {
    "name": "morpheus",
    "job": "leader"
}
Response 201
Response
{
    "name": "morpheus",
    "job": "leader",
    "id": "66",
    "createdAt": "2022-04-12T15:00:43.074Z"
}
     */
    void createUserTests201() {
        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")//post!
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
        //.body("body.createdAt", is("2022-04-12T15:00:43.074Z")) //??
        ;
    }

//-----8? как оформить, если проверочные даныне это фрагмент массива?
    @Test
    void delayedResponse2() {

        /*
        request: https://reqres.in/api/users?delay=3
        response: 200
        response:

        {
    "page": 1,
    "per_page": 6,
    "total": 12,
    "total_pages": 2,
    "data": [
        {
            "id": 1,
            "email": "george.bluth@reqres.in",
            "first_name": "George",
            "last_name": "Bluth",
            "avatar": "https://reqres.in/img/faces/1-image.jpg"
        },
        {
            "id": 2,
            "email": "janet.weaver@reqres.in",
            "first_name": "Janet",
            "last_name": "Weaver",
            "avatar": "https://reqres.in/img/faces/2-image.jpg"
        },
        {
            "id": 3,
            "email": "emma.wong@reqres.in",
            "first_name": "Emma",
            "last_name": "Wong",
            "avatar": "https://reqres.in/img/faces/3-image.jpg"
        },
        ....................
        }
    ],
    "support": {
        "url": "https://reqres.in/#support-heading",
        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
    }
}
    */
        given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .body("total", is(12))
                .body("per_page", is(6))
                //.body("data.id", is(1)) //?
                //.body("data.email", is("george.bluth@reqres.in")) //??
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

}

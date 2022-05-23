package BookingTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("API Rest Tests of The Create Booking Module")
public class CreateBookingTest {
    private String token;

    @BeforeEach
    public void beforeEach(){
        // Configuring Rest API data
        baseURI = "https://restful-booker.herokuapp.com";

        //Get the user's Token
        this.token = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when()
                .post("/auth")
                .then()
                .extract()
                .path("token");
    }


    @Test
    @DisplayName("Validate the Creation of a new Booking")
    public void testValidateCreationNewBooking(){



        //Insert valid parameters for Booking
            given()
                    .contentType(ContentType.JSON)
                    .header("token", this.token)
                    .body("{\n" +
                                    "    \"firstname\" : \"Jim\",\n" +
                                    "    \"lastname\" : \"Brown\",\n" +
                                    "    \"totalprice\" : 111,\n" +
                                    "    \"depositpaid\" : true,\n" +
                                    "    \"bookingdates\" : {\n" +
                                    "        \"checkin\" : \"2018-01-01\",\n" +
                                    "        \"checkout\" : \"2019-01-01\"\n" +
                                    "    },\n" +
                                    "    \"additionalneeds\" : \"Breakfast\"\n" +
                                    "}")
        // Status Code returned is 200
            .when()
                    .post("/booking")
            .then()
                    .assertThat()
                    .statusCode(200);
    }
    @Test
    @DisplayName("Create a Booking without firstname")
    public void testCreateBookingWithoutFirstname(){

        //Insert invalid parameters for Booking
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "    \"firstname\" : \n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                // Status Code returned is 400
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .statusCode(400);

    }
    @Test
    @DisplayName("Create a Booking with int values in firstname field")
    public void testCreateBookingWithIntInFirstname() {


        //Insert valid parameters for Booking
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "    \"firstname\" : 123,\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                // Status Code returned is 500
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .statusCode(500);

    }



}

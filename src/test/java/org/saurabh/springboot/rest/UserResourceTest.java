package org.saurabh.springboot.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.saurabh.springboot.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({"server.port=9090"})
public class UserResourceTest {

    @Value("${local.server.port}")
    private int serverPort;

    @Test
    public void saveUser() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("gender", "MALE").node("centre", "SOUTHALL")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat().statusCode(204);
    }

    @Test
    public void donotSaveIfGenderIsNotProvided() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("centre", "SOUTHALL")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.gender.code", equalTo("null_gender"))
                .body("errors.gender.message", equalTo("Please provide a \"gender\" in the request body; possible values are MALE, " +
                        "FEMALE, OTHER"))
                .statusCode(400);
    }

    @Test
    public void donotSaveIfCentreIsNotProvided() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("gender", "MALE")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.centre.code", equalTo("null_centre"))
                .statusCode(400);
    }

    @Test
    public void donotSaveIfGenderAndCentreAreNotProvided() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder()
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.gender.code", equalTo("null_gender"))
                .body("errors.centre.code", equalTo("null_centre"))
                .statusCode(400);
    }

    @Test
    public void donotSaveIfGenderIsInvalid() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("gender", "INVALID-GENDER").node("centre", "SOUTHALL")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.gender.code", equalTo("invalid_gender"))
                .body("errors.gender.message", is(notNullValue()))
                .statusCode(400);
    }

    @Test
    public void donotSaveIfCentreIsInvalid() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("gender", "MALE").node("centre", "INVALID_CENTRE")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.centre.code", equalTo("invalid_centre"))
                .body("errors.centre.message", is(notNullValue()))
                .statusCode(400);
    }

    @Test
    public void donotSaveIfGenderAndCentreBothAreInvalid() throws Exception {
        given().contentType("application/json").body(new JsonStringBuilder().node("gender", "INVALID_GENDER")
                .node("centre", "INVALID_CENTRE")
                .node("phoneNumber", "07528114111").node("firstName", "saurabh").node("lastName", "arora")
                .node("dateOfBirth", "{\"day\":3, \"month\":7, \"year\":1980}").build()).post(to("/user"))
                .then().assertThat()
                .body("errors.gender.code", equalTo("invalid_gender"))
                .body("errors.centre.code", equalTo("invalid_centre"))
                .statusCode(400);
    }

    private String to(String path) {
        return "http://localhost:" + serverPort + path;
    }

    class JsonStringBuilder {

        private Map<String, String> nodes = new HashMap<>();

        public JsonStringBuilder node(String name, String value) {
            nodes.put(name, value);
            return this;
        }

        public String build() {
            List<String> stringNodes = new ArrayList<>();
            for (String name : nodes.keySet()) {
                String value = nodes.get(name);
                if (value.startsWith("{") && value.endsWith("}")) {
                    stringNodes.add(String.format("\"%s\":%s", name, value));
                } else {
                    stringNodes.add(String.format("\"%s\":\"%s\"", name, value));
                }
            }
            StringBuilder jsonString = new StringBuilder("{");
            for (int i = 0; i < stringNodes.size(); i++) {
                if (i > 0) {
                    jsonString.append(",");
                }
                jsonString.append(stringNodes.get(i));
            }
            return jsonString.append("}").toString();
        }
    }
}

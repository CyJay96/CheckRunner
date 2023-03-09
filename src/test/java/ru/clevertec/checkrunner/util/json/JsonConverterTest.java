package ru.clevertec.checkrunner.util.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.builder.json.PersonTestBuilder;
import ru.clevertec.checkrunner.util.json.data.Person;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterTest {

    private Person person;

    private String json;

    @BeforeEach
    void setUp() {
        person = PersonTestBuilder.aPerson().build();
        json = "{\"name\":\"Tom\",\"age\":21,\"address\":{\"house\":{\"floorCount\":5},\"isApartment\":true,\"houseNumber\":40},\"gender\":\"M\"}";
    }

    @Test
    @DisplayName("Convert object to JSON")
    void checkToJsonShouldReturnObjectJsonString() {
        String actualJson = JsonConverter.toJson(person);
        assertThat(actualJson).isEqualTo(json);
    }

    @Test
    @DisplayName("Convert JSON to object")
    void checkFromJsonShouldReturnObject() {
        Person actualPerson = JsonConverter.fromJson(json, Person.class);
        assertThat(actualPerson).isEqualTo(person);
    }
}
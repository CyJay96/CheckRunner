package ru.clevertec.checkrunner.util.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.util.json.data.Person;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterTest {

    private Person person;

    private String json;

    @BeforeEach
    void setUp() {
        person = new Person();
        json = "{\"name\":\"Tom\",\"age\":21,\"address\":{\"house\":{\"floors\":[{\"width\":8,\"height\":6.4},{\"width\":8,\"height\":6.4}],\"receipts\":[1.2,4.6]},\"isApartment\":true,\"houseNumber\":40},\"gender\":\"M\"}";
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
package com.pagoda.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testfixtures.PersonDTOFixture;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void shouldCreatePersonIntegration() {
        PersonDTO input = PersonDTOFixture.personDTOBuilder().build();

        String responseJson = mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PersonDTO actual = objectMapper.readValue(responseJson, PersonDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(input);

        assertThat(actual.id()).isNotNull();
    }
}
package com.example.overnightRest.endpoint.admin;

import com.example.common.entity.Attribute;
import com.example.common.repository.AttributeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AttributeEndpointTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AttributeRepository attributeRepository;

    private static final String BASE_URL = "http://localhost:8081";

    @BeforeEach
    public void beforeAll() {
        attributeRepository.deleteAll();
    }

    @Test
    public void getAllAttributesEndpoint() throws Exception {
        addTestAttribute();
        mvc.perform(get(BASE_URL + "/attribute")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void addAttributeEndpoint() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "WiFi");

        //save attribute
        mvc.perform(post(BASE_URL + "/attribute/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());

        //get all attribute
        mvc.perform(get(BASE_URL + "/attribute")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private void addTestAttribute() {
        attributeRepository.save(Attribute.builder()
                .name("WiFi")
                .build());
        attributeRepository.save(Attribute.builder()
                .name("spa")
                .build());
    }

}
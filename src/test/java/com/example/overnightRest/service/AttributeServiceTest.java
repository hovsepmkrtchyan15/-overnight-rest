package com.example.overnightRest.service;

import com.example.common.entity.Attribute;
import com.example.common.repository.AttributeRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AttributeServiceTest {
    @MockBean
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeService attributeService;

    @Test
    void findAll() {
        List<Attribute> attributes = Arrays.asList(
                new Attribute(1, "WiFi"),
                new Attribute(2, "Spa"));

        when(attributeRepository.findAll()).thenReturn(attributes);

        List<Attribute> all = attributeService.getAll();
        assertEquals(all, attributes);

        verify(attributeRepository, times(1)).findAll();
    }

    @Test
    void findById() throws EntityNotFoundException {
        int id = 1;
        Attribute attribute = Attribute.builder()
                .id(1)
                .name("WiFi")
                .build();
        Optional<Attribute> attribute1 = Optional.ofNullable(attribute);

        when(attributeRepository.findById(id)).thenReturn(attribute1);

        Optional<Attribute> byId = attributeService.findById(id);
        assertEquals(byId, attribute1);
        verify(attributeRepository, times(1)).findById(id);
    }

    @Test
    void findByIdException() {

        int id = 0;

        when(attributeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            attributeService.findById(id);
        });

    }
}

package com.example.overnightRest.service;

import com.example.common.entity.Attribute;
import com.example.common.entity.Region;
import com.example.common.repository.RegionRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegionServiceTest {

    @MockBean
    RegionRepository regionRepository;

    @Autowired
    RegionService regionService;

    @Test
    void findAll() {
        List<Region> regions = Arrays.asList(
                new Region(1, "Shirak"),
                new Region(2, "Lori"));

        when(regionRepository.findAll()).thenReturn(regions);

        List<Region> all = regionService.getAll();
        assertEquals(all, regions);

        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void findById() throws EntityNotFoundException {
        Region region = Region.builder()
                .id(1)
                .name("Shirak")
                .build();
        Optional<Region> regionOptional = Optional.ofNullable(region);

        when(regionRepository.findById(1)).thenReturn(regionOptional);

        Optional<Region> byId = regionService.findById(1);
        assertEquals(byId, regionOptional);

        verify(regionRepository, times(1)).findById(1);
    }
    @Test
    void findByIdException() {

        int id = 0;

        when(regionRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            regionService.findById(id);
        });
    }
    @Test
    void deleteById() throws EntityNotFoundException {
        Region region = Region.builder()
                .id(2)
                .name("Lori")
                .build();
        when(regionRepository.findById(any())).thenReturn(Optional.ofNullable(region));

        regionService.deleteById(2);
        verify(regionRepository, times(1)).deleteById(2);
    }

    @Test
    void deleteByIdNull()  {

        assertThrows(EntityNotFoundException.class, () -> {
            regionService.findById(0);
        });
    }
}

package com.example.overnightRest.service;


import com.example.common.entity.Region;
import com.example.common.repository.RegionRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    public Optional<Region> findById(int id) {
        return regionRepository.findById(id);
    }

    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    public void save(Region region) {
        regionRepository.save(region);
    }

    public void update(Region region) throws EntityNotFoundException {
        Optional<Region> byId = regionRepository.findById(region.getId());
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Region whit id = " + region.getId() + " does not exists");
        }
        regionRepository.save(region);
    }

    public void deleteById(int id) throws EntityNotFoundException {
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Region whit id = " + id + " does not exists");
        }
        regionRepository.deleteById(id);
    }
}

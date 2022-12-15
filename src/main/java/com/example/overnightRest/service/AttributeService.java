package com.example.overnightRest.service;


import com.example.common.entity.Attribute;
import com.example.common.entity.Region;
import com.example.common.repository.AttributeRepository;
import com.example.common.repository.RegionRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.AttributeMapper;
import com.example.overnightRest.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    private final AttributeMapper attributeMapper;

    public List<Attribute> getAll() {
        return attributeRepository.findAll();
    }

    public Optional<Attribute> findById(int id) {
        return attributeRepository.findById(id);
    }

    public Optional<Attribute> findByName(String name) {
        return attributeRepository.findByName(name);
    }

    public void save(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public void update(Attribute attribute) throws EntityNotFoundException {
        Optional<Attribute> byId = attributeRepository.findById(attribute.getId());
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Attribute whit id = " + attribute.getId()+ " does not exists");
        }
        attributeRepository.save(attribute);
    }

    public void deleteById(int id) throws EntityNotFoundException {
        Optional<Attribute> byId = attributeRepository.findById(id);
        if(byId.isEmpty()){
            throw new EntityNotFoundException("Attribute whit id = " + id+ " does not exists");
        }
        attributeRepository.deleteById(id);
    }
}

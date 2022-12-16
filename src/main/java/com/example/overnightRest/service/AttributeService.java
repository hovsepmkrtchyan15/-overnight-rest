package com.example.overnightRest.service;


import com.example.common.entity.Attribute;
import com.example.common.repository.AttributeRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.AttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public List<Attribute> getAll() {
        return attributeRepository.findAll();
    }

    public Optional<Attribute> findById(int id) throws EntityNotFoundException {
        Optional<Attribute> byId = attributeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Attribute whit id = " + id + " does not exists");
        }
        return byId;
    }

    public Optional<Attribute> findByName(String name) {
        return attributeRepository.findByName(name);
    }

    public void save(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public void update(Attribute attribute) throws EntityNotFoundException {
        final int id = attribute.getId();
        attribute = attributeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Attribute whit id = %s does not exists", id))
        );
        attributeRepository.save(attribute);
    }

    public void deleteById(int id) throws EntityNotFoundException {
        Optional<Attribute> byId = attributeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Attribute whit id = " + id + " does not exists");
        }
        attributeRepository.deleteById(id);
    }
}

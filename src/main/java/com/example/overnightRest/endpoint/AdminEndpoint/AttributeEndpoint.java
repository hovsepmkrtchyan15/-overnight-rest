package com.example.overnightRest.endpoint.AdminEndpoint;

import com.example.common.entity.Attribute;
import com.example.common.entity.Region;
import com.example.overnightRest.dto.AttributeCreateDto;
import com.example.overnightRest.dto.AttributeDto;
import com.example.overnightRest.dto.RegionCreateDto;
import com.example.overnightRest.dto.RegionDto;
import com.example.overnightRest.mapper.AttributeMapper;
import com.example.overnightRest.mapper.RegionMapper;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.AttributeService;
import com.example.overnightRest.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/attribute")
public class AttributeEndpoint {

    private final AttributeService attributeService;
    private final AttributeMapper attributeMapper;


    @GetMapping("")
    public ResponseEntity<List<AttributeDto>> getAttributes() {
        List<Attribute> allAttributes = attributeService.getAll();
        return ResponseEntity.ok(attributeMapper.map(allAttributes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDto> getAttributeById(@PathVariable("id") int id) {
        Optional<Attribute> byId = attributeService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(attributeMapper.map(byId.get()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAttribute(@RequestBody AttributeCreateDto attributeCreateDto) {
        Optional<Attribute> byName = attributeService.findByName(attributeCreateDto.getName());
        if (byName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        attributeService.save(attributeMapper.map(attributeCreateDto));
        return ResponseEntity.ok(attributeCreateDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAttribute(@RequestBody AttributeDto attributeDto) {
        if (attributeDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        attributeService.update(attributeMapper.map(attributeDto));
        return ResponseEntity.ok(attributeDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @AuthenticationPrincipal CurrentUser currentUser) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        attributeService.deleteById(id);
        log.info("Attribute whit id = " + id + "  was removed by user " + currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}

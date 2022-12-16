package com.example.overnightRest.endpoint.admin;

import com.example.common.dto.RegionCreateDto;
import com.example.common.dto.RegionDto;
import com.example.common.entity.Region;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.RegionMapper;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/region")
public class RegionEndpoint {

    private final RegionService regionService;
    private final RegionMapper regionMapper;


    @GetMapping("")
    public ResponseEntity<List<RegionDto>> getRegions() {
        List<Region> allRegions = regionService.getAll();
        return ResponseEntity.ok(regionMapper.map(allRegions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDto> getRegionById(@PathVariable("id") int id) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regionMapper.map(byId.get()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRegion(@Valid @RequestBody RegionCreateDto regionCreateDto) {
        Optional<Region> byName = regionService.findByName(regionCreateDto.getName());
        if (byName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        regionService.save(regionMapper.map(regionCreateDto));
        return ResponseEntity.ok(regionCreateDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRegion(@Valid @RequestBody RegionDto regionDto) throws EntityNotFoundException {
        if (regionDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        regionService.update(regionMapper.map(regionDto));
        return ResponseEntity.ok(regionDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        regionService.deleteById(id);
        log.info("Region whit id = {} was removed by user {} ", id, currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}

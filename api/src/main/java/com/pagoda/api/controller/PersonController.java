package com.pagoda.api.controller;

import com.pagoda.application.*;
import com.pagoda.core.model.Person;
import com.pagoda.shared.mapper.PersonMapper;
import com.pagoda.shared.model.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    private final CreatePersonCommandHandler createPersonCommandHandler;
    private final SearchPersonQuery searchPersonQuery;
    private final UpdatePersonCommandHandler updatePersonCommandHandler;
    private final GeneratePersonReport reportService;
    private final PersonMapper personMapper;

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO dto) {
        PersonWithID created = createPersonCommandHandler.handle(personMapper.toDomain(dto));
        return personMapper.toDto(created.person(), created.id());
    }

    @GetMapping
    public List<PersonDTO> getPersonByName(@RequestParam(required = false) String name) {
        List<PersonWithID> persons;
        if (name != null && !name.trim().isEmpty()) {
            persons = searchPersonQuery.findByName(name);
        } else {
            persons = searchPersonQuery.findAll();
        }
        return persons.stream()
                .map(p -> personMapper.toDto(p.person(), p.id()))
                .toList();
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable UUID id, @RequestBody PersonDTO dto) {
        Person updated = personMapper.toDomain(dto);
        PersonWithID result = updatePersonCommandHandler.handle(id, updated);
        return personMapper.toDto(result.person(), result.id());
    }


    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadSinglePersonPdf(@PathVariable UUID id) {
        PersonWithID person = searchPersonQuery.findById(id);
        byte[] pdf = reportService.generateSinglePersonReport(person);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=person-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> downloadPersonListPdf() {
        List<PersonWithID> persons = searchPersonQuery.findAll();
        byte[] pdf = reportService.generatePersonListReport(persons);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=persons-list.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}

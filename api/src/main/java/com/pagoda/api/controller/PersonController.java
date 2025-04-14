package com.pagoda.api.controller;

import com.pagoda.application.CreatePersonCommandHandler;
import com.pagoda.application.PersonWithID;
import com.pagoda.shared.mapper.PersonMapper;
import com.pagoda.shared.model.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    private final CreatePersonCommandHandler createPersonCommandHandler;
    private final PersonMapper personMapper;

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO dto) {
        PersonWithID created = createPersonCommandHandler.handle(personMapper.toDomain(dto));
        return personMapper.toDto(created.person(), created.id());
    }

}

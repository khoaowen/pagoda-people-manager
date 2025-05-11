package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import com.pagoda.shared.testfixtures.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePersonCommandJpaAdapterTest {
    @InjectMocks
    private CreatePersonCommandJpaAdapter createPersonCommandJpaAdapter;
    @Mock
    private PersonRepository personRepository;
    @Spy
    private PersonEntityMapper personEntityMapper = Mappers.getMapper(PersonEntityMapper.class);

    @Test
    @DisplayName("Should create person and return with ID")
    void shouldCreatePersonAndReturnWithId() {
        // given
        Person person = PersonFixture.builder().build();
        PersonEntity personEntity = personEntityMapper.toEntity(person);
        PersonEntity savedPersonEntity = personEntityMapper.toEntity(person);
        savedPersonEntity.setId(UUID.randomUUID());
        when(personRepository.save(personEntity)).thenReturn(savedPersonEntity);

        // when
        PersonWithID personWithID = createPersonCommandJpaAdapter.handle(person);

        // then
        assertThat(personWithID.person()).isEqualTo(person);
        assertThat(personWithID.id()).isEqualTo(savedPersonEntity.getId());
    }
}
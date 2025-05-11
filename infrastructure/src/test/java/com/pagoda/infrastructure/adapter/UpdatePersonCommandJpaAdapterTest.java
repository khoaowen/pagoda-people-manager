package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.application.exception.PersonNotFoundException;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import com.pagoda.infrastructure.testfixtures.PersonEntityFixture;
import com.pagoda.shared.testfixtures.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePersonCommandJpaAdapterTest {

    @Mock
    private PersonRepository repository;

    @Spy
    private PersonEntityMapper mapper = Mappers.getMapper(PersonEntityMapper.class);

    @InjectMocks
    private UpdatePersonCommandJpaAdapter adapter;

    @Test
    @DisplayName("Should update and return updated person")
    void shouldUpdatePerson() {
        UUID id = UUID.randomUUID();
        var original = PersonEntityFixture.personEntityBuilder().id(id).firstName("Old").build();
        var updated = PersonFixture.builder().firstName("New").build();

        when(repository.findById(id)).thenReturn(Optional.of(original));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        PersonWithID result = adapter.handle(id, updated);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.person()).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    @DisplayName("Should throw when person not found")
    void shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();
        var updated = PersonFixture.builder().build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.handle(id, updated))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
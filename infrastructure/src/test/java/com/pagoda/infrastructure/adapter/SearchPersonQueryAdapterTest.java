package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import com.pagoda.infrastructure.testfixtures.PersonEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPersonQueryAdapterTest {

    @Mock
    private PersonRepository repository;

    @Spy
    private PersonEntityMapper mapper = Mappers.getMapper(PersonEntityMapper.class);

    @InjectMocks
    private SearchPersonQueryAdapter adapter;

    @Test
    @DisplayName("Should return mapped persons matching name")
    void shouldReturnMappedPersonsMatchingName() {
        var entity = PersonEntityFixture.personEntityBuilder().build();
        var domain = mapper.toDomain(entity);
        var id = entity.getId();

        when(repository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase("tho", "tho"))
                .thenReturn(List.of(entity));

        List<PersonWithID> result = adapter.findByName("tho");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().person()).usingRecursiveComparison().isEqualTo(domain);
        assertThat(result.getFirst().id()).isEqualTo(id);
    }
}
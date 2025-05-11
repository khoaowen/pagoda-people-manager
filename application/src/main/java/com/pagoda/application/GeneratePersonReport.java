package com.pagoda.application;

import java.util.List;

public interface GeneratePersonReport {
    byte[] generateSinglePersonReport(PersonWithID personWithID);

    byte[] generatePersonListReport(List<PersonWithID> personWithID);
}

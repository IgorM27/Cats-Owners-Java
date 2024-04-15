package org.igorM.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OwnerDTO {
    private final int id;
    private final String name;
    private final LocalDate birth;
    private final List<CatDTO> cats;

    public OwnerDTO(int id, String name, LocalDate birth, List<CatDTO> cats) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.cats = cats;
    }
}
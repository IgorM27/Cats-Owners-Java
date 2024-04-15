package org.igorM.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CatDTO {
    private final int id;
    private final String name;
    private final int ownerId;
    private final String breed;
    private final String colour;
    private final LocalDate birth;
    private final List<Integer> friendsId;

    public CatDTO(int id, String name, int ownerId, String breed, String colour, LocalDate birth, List<Integer> friendsId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.breed = breed;
        this.colour = colour;
        this.birth = birth;
        this.friendsId = friendsId;
    }
}
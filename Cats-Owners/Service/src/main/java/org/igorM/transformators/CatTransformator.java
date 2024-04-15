package org.igorM.transformators;

import org.igorM.dto.CatDTO;
import org.igorM.entity.Cat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatTransformator {
    public CatDTO castDTO(Cat cat){
        Objects.requireNonNull(cat);
        List<Integer> friends = new ArrayList<>();
        for (Cat tmpCat : cat.getFriends()) {
            friends.add(tmpCat.getId());
        }
        return new CatDTO(cat.getId(), cat.getName(), cat.getOwner().getId(), cat.getBreed().name(), cat.getColour().name(), cat.getBirth(), friends);
    }
}

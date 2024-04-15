package org.igorM.transformators;

import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.entity.Cat;
import org.igorM.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerTransformator {
    public OwnerDTO castDTO(Owner owner){
        Objects.requireNonNull(owner);
        List<CatDTO> cats = new ArrayList<>();
        CatTransformator transformator = new CatTransformator();
        for (Cat cat : owner.getCats()) {
            cats.add(transformator.castDTO(cat));
        }
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirth(), cats);
    }
}

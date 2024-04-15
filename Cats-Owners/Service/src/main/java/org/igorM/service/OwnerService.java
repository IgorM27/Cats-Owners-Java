package org.igorM.service;

import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDTO createOwner(String name, LocalDate birth);
    OwnerDTO getOwnerById(int id);
    List<OwnerDTO> getAllOwners();
    void deleteOwner(int id);
    void addCat(int ownerId, int catId);
    List<CatDTO> getAllCats(int id);
}

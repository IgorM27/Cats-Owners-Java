package org.igorM.controller;

import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.controller.startDTO.StartOwnerDTO;

import java.util.List;

public interface OwnerController {
    OwnerDTO createOwner(StartOwnerDTO ownerDTO);
    OwnerDTO getOwnerById(int id);
    List<OwnerDTO> getAllOwners();
    String deleteOwner(int id);
    String addCat(int ownerId, int catId);
    List<CatDTO> getAllCats(int id);
}

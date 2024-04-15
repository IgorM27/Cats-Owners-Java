package org.igorM.controller;

import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.service.OwnerService;
import org.igorM.controller.startDTO.StartOwnerDTO;

import java.util.List;

public class OwnerControllerImpl implements OwnerController {
    private final OwnerService service;

    public OwnerControllerImpl(OwnerService service) {
        this.service = service;
    }

    @Override
    public OwnerDTO createOwner(StartOwnerDTO ownerDTO) { return service.createOwner(ownerDTO.name(), ownerDTO.birthDate()); }

    @Override
    public OwnerDTO getOwnerById(int id) {
        return service.getOwnerById(id);
    }

    @Override
    public List<OwnerDTO> getAllOwners() {
        return service.getAllOwners();
    }

    @Override
    public String deleteOwner(int id) {
        service.deleteOwner(id);
        return "Owner with id: " + id + " was successfully deleted";
    }

    @Override
    public String addCat(int ownerId, int catId) {
        service.addCat(ownerId, catId);
        return "Cat with id: " + catId + " was successfully added to owner with id: " + ownerId;
    }

    @Override
    public List<CatDTO> getAllCats(int id) {
        return service.getAllCats(id);
    }
}

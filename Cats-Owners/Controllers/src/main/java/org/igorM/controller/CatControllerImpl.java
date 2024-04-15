package org.igorM.controller;

import org.igorM.dto.CatDTO;
import org.igorM.exception.CatServiceException;
import org.igorM.service.CatService;
import org.igorM.controller.startDTO.StartCatDTO;

import java.util.List;

public class CatControllerImpl implements CatController{
    private final CatService service;

    public CatControllerImpl(CatService service) {
        this.service = service;
    }

    @Override
    public CatDTO createCat(StartCatDTO catDTO) throws CatServiceException {
        return service.createCat(catDTO.name(), catDTO.ownerId(), catDTO.breed(), catDTO.colour(), catDTO.birthDate());
    }

    @Override
    public String deleteCat(int id) throws CatServiceException {
     service.deleteCat(id);
     return "Cat with id: " + id + " was successfully deleted";
    }

    @Override
    public CatDTO getCatById(int id) {
        return service.getCatById(id);
    }

    @Override
    public List<CatDTO> getAllCats() {
        return service.getAllCats();
    }

    @Override
    public List<CatDTO> getAllFriends(int id) {
        return service.getAllCats();
    }

    @Override
    public List<CatDTO> getCatsByBreed(String breedName) { return service.getCatsByBreed(breedName); }

    @Override
    public List<CatDTO> getCatsByColour(String colourName) { return service.getCatsByColour(colourName); }

    @Override
    public List<CatDTO> getCatsByColourAndBreed(String colourName, String breedName) { return service.getCatsByColourAndBreed(colourName, breedName); }

    @Override
    public String makeFriends(int firstCatId, int secondCatId) {
        service.makeFriends(firstCatId, secondCatId);
        return "Cat with id: " + firstCatId + " and cat with id: " + secondCatId + " now are friends";
    }

    @Override
    public String unfriend(int firstCatId, int secondCatId) {
        service.unfriend(firstCatId,secondCatId);
        return "Cat with id: " + firstCatId + " and cat with id: " + secondCatId + " now aren`t friends";
    }
}

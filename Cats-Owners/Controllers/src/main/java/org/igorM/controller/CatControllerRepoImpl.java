package org.igorM.controller;

import jakarta.validation.Valid;
import org.igorM.controller.startDTO.StartCatDTO;
import org.igorM.dto.CatDTO;
import org.igorM.exception.CatServiceException;
import org.igorM.service.CatServiceRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat/")
public class CatControllerRepoImpl implements CatController {
    private final CatServiceRepoImpl service;

    @Autowired
    public CatControllerRepoImpl(CatServiceRepoImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping()
    public CatDTO createCat(@Valid @RequestBody StartCatDTO catDTO) throws CatServiceException {
        return service.createCat(catDTO.name(), catDTO.ownerId(), catDTO.breed(), catDTO.colour(), catDTO.birthDate());
    }

    @Override
    @DeleteMapping("/{id}")
    public String deleteCat(@PathVariable("id") int id) throws CatServiceException {
        service.deleteCat(id);
        return "Cat with id: " + id + " was successfully deleted";
    }

    @Override
    @GetMapping("{id}")
    public CatDTO getCatById(@PathVariable("id") int id) {
        return service.getCatById(id);
    }

    @Override
    @GetMapping()
    public List<CatDTO> getAllCats() {
        return service.getAllCats();
    }

    @Override
    @GetMapping("friends/{id}")
    public List<CatDTO> getAllFriends(@PathVariable("id") int id) {
        return service.getAllFriends(id);
    }

    @Override
    @GetMapping("getBreed")
    public List<CatDTO> getCatsByBreed(@RequestParam(name = "breed") String breedName) {
        return service.getCatsByBreed(breedName);
    }

    @Override
    @GetMapping("getColour")
    public List<CatDTO> getCatsByColour(@RequestParam(name = "colour") String colourName) {
        return service.getCatsByColour(colourName);
    }

    @Override
    @GetMapping("getColourBreed")
    public List<CatDTO> getCatsByColourAndBreed(@RequestParam(name = "colour") String colourName, @RequestParam(name = "breed") String breedName) {
        return service.getCatsByColourAndBreed(colourName, breedName);
    }

    @Override
    @PutMapping("makeFriends")
    public String makeFriends(@RequestParam(name = "cat1") int firstCatId, @RequestParam(name = "cat2") int secondCatId) {
        service.makeFriends(firstCatId, secondCatId);
        return "Cat with id: " + firstCatId + " and cat with id: " + secondCatId + " now are friends";
    }

    @Override
    @PutMapping("unfriend")
    public String unfriend(@RequestParam(name = "cat1") int firstCatId, @RequestParam(name = "cat2") int secondCatId) {
        service.unfriend(firstCatId, secondCatId);
        return "Cat with id: " + firstCatId + " and cat with id: " + secondCatId + " now aren`t friends";
    }
}

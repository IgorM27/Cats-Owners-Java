package org.igorM.controller;

import jakarta.validation.Valid;
import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.service.OwnerServiceRepoImpl;
import org.igorM.controller.startDTO.StartOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/")
public class OwnerControllerRepoImpl implements OwnerController{
    private final OwnerServiceRepoImpl service;

    @Autowired
    public OwnerControllerRepoImpl(OwnerServiceRepoImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping()
    public OwnerDTO createOwner(@Valid @RequestBody StartOwnerDTO ownerDTO) {
        return service.createOwner(ownerDTO.name(), ownerDTO.birthDate());
    }

    @Override
    @GetMapping("{id}")
    public OwnerDTO getOwnerById(@PathVariable("id") int id) {
        return service.getOwnerById(id);
    }

    @Override
    @GetMapping()
    public List<OwnerDTO> getAllOwners() {
        return service.getAllOwners();
    }

    @Override
    @DeleteMapping("{id}")
    public String deleteOwner(@PathVariable("id") int id) {
        service.deleteOwner(id);
        return "Owner with id: " + id + " was successfully deleted";
    }

    @Override
    @PutMapping("addCat")
    public String addCat(@RequestParam(name = "owner") int ownerId, @RequestParam(name = "cat") int catId) {
        service.addCat(ownerId, catId);
        return "Cat with id: " + catId + " was successfully added to owner with id: " + ownerId;
    }

    @Override
    @GetMapping("cats/{id}")
    public List<CatDTO> getAllCats(@PathVariable("id") int id) {
        return service.getAllCats(id);
    }
}

package org.igorM.service;

import org.igorM.dto.CatDTO;
import org.igorM.entity.Cat;
import org.igorM.exception.CatServiceException;
import org.igorM.models.Breed;
import org.igorM.models.Colour;
import org.igorM.repositories.CatRepository;
import org.igorM.repositories.OwnerRepository;
import org.igorM.transformators.CatTransformator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class CatServiceRepoImpl implements CatService{
    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CatServiceRepoImpl(CatRepository catRepository, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public CatDTO createCat(String name, int ownerId, String breed, String colour, LocalDate birth) throws CatServiceException {
        if (!ownerRepository.existsById(ownerId)) {
            throw CatServiceException.noOwner(ownerId);
        }
        var tmp_colour = Colour.None;
        for (int i = 0; i < Colour.values().length; i++) {
            if (Colour.values()[i].name().equals(colour)) {
                tmp_colour = Colour.valueOf(colour);
            }
        }
        var tmp_breed = Breed.None;
        for (int i = 0; i < Breed.values().length; i++) {
            if (Breed.values()[i].name().equals(breed)) {
                tmp_breed = Breed.valueOf(breed);
            }
        }
        var owner = ownerRepository.getReferenceById(ownerId);
        var cat = new Cat(name, owner, tmp_breed, tmp_colour, birth, new ArrayList<>());
        owner.addCat(cat);
        catRepository.save(cat);
        var transformator = new CatTransformator();
        return transformator.castDTO(cat);
    }

    @Override
    public void deleteCat(int id) throws CatServiceException {
        if (!catRepository.existsById(id)) {
            throw CatServiceException.noCat(id);
        }
        var cat = catRepository.getReferenceById(id);
        for (Cat tmp : cat.getFriends()){
            tmp.getFriends().remove(cat);
        }
        cat.getOwner().getCats().remove(cat);
        cat.getFriends().clear();

        catRepository.save(cat);
        catRepository.deleteById(id);
    }

    @Override
    public CatDTO getCatById(int id) {
        if (!catRepository.existsById(id)) {
            throw CatServiceException.noCat(id);
        }
        var cat = catRepository.getReferenceById(id);
        var transformator = new CatTransformator();
        return transformator.castDTO(cat);
    }

    @Override
    public List<CatDTO> getAllCats() {
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catRepository.findAll()){
            cats.add(transformator.castDTO(tmp));
        }
        return cats;
    }

    @Override
    public List<CatDTO> getAllFriends(int id) {
        if (!catRepository.existsById(id)) {
            throw CatServiceException.noCat(id);
        }
        var cat = catRepository.getReferenceById(id);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : cat.getFriends()){
            cats.add(transformator.castDTO(tmp));
        }
        return cats;
    }

    @Override
    public List<CatDTO> getCatsByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catRepository.findAll()){
            if (tmp.getBreed().equals(breed)){
                cats.add(transformator.castDTO(tmp));
            }
        }
        return cats;
    }

    @Override
    public List<CatDTO> getCatsByColour(String colourName) {
        Colour colour = Colour.valueOf(colourName);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catRepository.findAll()){
            if (tmp.getColour().equals(colour)){
                cats.add(transformator.castDTO(tmp));
            }
        }
        return cats;
    }

    @Override
    public List<CatDTO> getCatsByColourAndBreed(String colourName, String breedName) {
        Colour colour = Colour.valueOf(colourName);
        Breed breed = Breed.valueOf(breedName);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catRepository.findAll()){
            if (tmp.getColour().equals(colour) && tmp.getBreed().equals(breed)){
                cats.add(transformator.castDTO(tmp));
            }
        }
        return cats;
    }

    @Override
    public void makeFriends(int firstCatId, int secondCatId) {
        if (!catRepository.existsById(firstCatId)) {
            throw CatServiceException.noCat(firstCatId);
        }
        var firstCat = catRepository.getReferenceById(firstCatId);
        if (!catRepository.existsById(secondCatId)) {
            throw CatServiceException.noCat(secondCatId);
        }
        var secondCat = catRepository.getReferenceById(secondCatId);
        firstCat.makeFriend(secondCat);
        secondCat.makeFriend(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }

    @Override
    public void unfriend(int firstCatId, int secondCatId) {
        if (!catRepository.existsById(firstCatId)) {
            throw CatServiceException.noCat(firstCatId);
        }
        var firstCat = catRepository.getReferenceById(firstCatId);
        if (!catRepository.existsById(secondCatId)) {
            throw CatServiceException.noCat(secondCatId);
        }
        var secondCat = catRepository.getReferenceById(secondCatId);
        firstCat.unfriend(secondCat);
        secondCat.unfriend(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }
}

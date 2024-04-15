package org.igorM.service;

import org.igorM.dao.CatDAO;
import org.igorM.dao.OwnerDAO;
import org.igorM.dto.CatDTO;
import org.igorM.entity.Cat;
import org.igorM.exception.CatServiceException;
import org.igorM.models.Breed;
import org.igorM.models.Colour;
import org.igorM.transformators.CatTransformator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatServiceImpl implements CatService {
    private final CatDAO catDAO;
    private final OwnerDAO ownerDAO;

    public CatServiceImpl(CatDAO catDAO, OwnerDAO ownerDAO) {
        this.catDAO = catDAO;
        this.ownerDAO = ownerDAO;
    }

    @Override
    public CatDTO createCat(String name, int ownerId, String breed, String colour, LocalDate birth) throws CatServiceException {
        var owner = ownerDAO.getOwnerById(ownerId);
        if (owner == null) {
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
        var cat = new Cat(name, owner, tmp_breed, tmp_colour, birth, new ArrayList<>());
        catDAO.addCat(cat);
        owner.addCat(cat);
        var transformator = new CatTransformator();
        return transformator.castDTO(cat);
    }

    @Override
    public void deleteCat(int id) throws CatServiceException {
        var cat = catDAO.getCatById(id);
        if (cat == null) {
            throw CatServiceException.noCat(id);
        }
        for (Cat tmp : catDAO.getAllFriends(id)){
            tmp.getFriends().remove(cat);
        }
        cat.getOwner().getCats().remove(cat);
        catDAO.removeCat(cat);
    }

    @Override
    public CatDTO getCatById(int id) {
        var cat = catDAO.getCatById(id);
        if (cat == null) {
            throw CatServiceException.noCat(id);
        }
        var transformator = new CatTransformator();
        return transformator.castDTO(cat);
    }

    @Override
    public List<CatDTO> getAllCats() {
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catDAO.getAll()){
            cats.add(transformator.castDTO(tmp));
        }
        return cats;
    }

    @Override
    public List<CatDTO> getAllFriends(int id) {
        var cat = catDAO.getCatById(id);
        if (cat == null) {
            throw CatServiceException.noCat(id);
        }
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catDAO.getAllFriends(id)){
            cats.add(transformator.castDTO(tmp));
        }
        return cats;
    }

    @Override
    public List<CatDTO> getCatsByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : catDAO.getAll()){
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
        for (Cat tmp : catDAO.getAll()){
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
        for (Cat tmp : catDAO.getAll()){
            if (tmp.getColour().equals(colour) && tmp.getBreed().equals(breed)){
                cats.add(transformator.castDTO(tmp));
            }
        }
        return cats;
    }

    @Override
    public void makeFriends(int firstCatId, int secondCatId) {
        var firstCat = catDAO.getCatById(firstCatId);
        if (firstCat == null) {
            throw CatServiceException.noCat(firstCatId);
        }
        var secondCat = catDAO.getCatById(secondCatId);
        if (secondCat == null) {
            throw CatServiceException.noCat(secondCatId);
        }
        firstCat.makeFriend(secondCat);
        secondCat.makeFriend(firstCat);
        catDAO.updateCat(firstCat);
    }

    @Override
    public void unfriend(int firstCatId, int secondCatId) {
        var firstCat = catDAO.getCatById(firstCatId);
        if (firstCat == null) {
            throw CatServiceException.noCat(firstCatId);
        }
        var secondCat = catDAO.getCatById(secondCatId);
        if (secondCat == null) {
            throw CatServiceException.noCat(secondCatId);
        }
        firstCat.unfriend(secondCat);
        secondCat.unfriend(firstCat);
        catDAO.updateCat(firstCat);
    }
}

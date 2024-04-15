package org.igorM.service;

import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.entity.Cat;
import org.igorM.entity.Owner;
import org.igorM.exception.CatServiceException;
import org.igorM.exception.OwnerServiceException;
import org.igorM.repositories.CatRepository;
import org.igorM.repositories.OwnerRepository;
import org.igorM.transformators.CatTransformator;
import org.igorM.transformators.OwnerTransformator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServiceRepoImpl implements OwnerService{
    private final OwnerRepository ownerRepository;
    private final CatRepository catRepository;

    @Autowired
    public OwnerServiceRepoImpl(OwnerRepository ownerRepository, CatRepository catRepository) {
        this.ownerRepository = ownerRepository;
        this.catRepository = catRepository;
    }

    @Override
    public OwnerDTO createOwner(String name, LocalDate birth) {
        var owner = new Owner(name, birth, new ArrayList<>());
        ownerRepository.save(owner);
        var transformator = new OwnerTransformator();
        return transformator.castDTO(owner);
    }

    @Override
    public OwnerDTO getOwnerById(int id) {
        if (!ownerRepository.existsById(id)){
            throw OwnerServiceException.noOwner(id);
        }
        var owner = ownerRepository.getReferenceById(id);
        var transformator = new OwnerTransformator();
        return transformator.castDTO(owner);
    }

    @Override
    public List<OwnerDTO> getAllOwners() {
        var owners = new ArrayList<OwnerDTO>();
        var transformator = new OwnerTransformator();
        for (Owner owner : ownerRepository.findAll()){
            owners.add(transformator.castDTO(owner));
        }
        return owners;
    }

    @Override
    public void deleteOwner(int id) {
        if (!ownerRepository.existsById(id)){
            throw OwnerServiceException.noOwner(id);
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public void addCat(int ownerId, int catId) {
        if (!ownerRepository.existsById(ownerId)){
            throw OwnerServiceException.noOwner(ownerId);
        }
        var owner = ownerRepository.getReferenceById(ownerId);
        if (!catRepository.existsById(catId)) {
            throw CatServiceException.noCat(catId);
        }
        var cat = catRepository.getReferenceById(catId);
        if (ownerRepository.existsById(cat.getOwner().getId())) {
            var owner_tmp = ownerRepository.getReferenceById(cat.getOwner().getId());
            owner_tmp.getCats().remove(cat);
            ownerRepository.save(owner_tmp);
        }
        owner.addCat(cat);
        cat.setOwner(owner);
        ownerRepository.save(owner);
        catRepository.save(cat);
    }

    @Override
    public List<CatDTO> getAllCats(int id) {
        if (!ownerRepository.existsById(id)){
            throw OwnerServiceException.noOwner(id);
        }
        var owner = ownerRepository.getReferenceById(id);
        var cats = new ArrayList<CatDTO>();
        var transformator = new CatTransformator();
        for (Cat tmp : owner.getCats()){
            cats.add(transformator.castDTO(tmp));
        }
        return cats;
    }
}

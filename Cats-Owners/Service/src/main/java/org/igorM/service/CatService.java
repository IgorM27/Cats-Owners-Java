package org.igorM.service;

import org.igorM.dto.CatDTO;
import org.igorM.exception.CatServiceException;

import java.time.LocalDate;
import java.util.List;

public interface CatService {
    CatDTO createCat(String name, int ownerId, String breed, String colour, LocalDate birth) throws CatServiceException;
    void deleteCat(int id) throws CatServiceException;
    CatDTO getCatById(int id);
    List<CatDTO> getAllCats();
    List<CatDTO> getAllFriends(int id);
    List<CatDTO> getCatsByBreed(String breedName);
    List<CatDTO> getCatsByColour(String colourName);
    List<CatDTO> getCatsByColourAndBreed(String colourName, String breedName);
    void makeFriends(int firstCatId, int secondCatId);
    void unfriend(int firstCatId, int secondCatId);

}

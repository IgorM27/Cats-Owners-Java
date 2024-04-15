package org.igorM.controller;

import org.igorM.dto.CatDTO;
import org.igorM.exception.CatServiceException;
import org.igorM.controller.startDTO.StartCatDTO;

import java.util.List;

public interface CatController {
    CatDTO createCat( StartCatDTO catDTO) throws CatServiceException;
    String deleteCat(int id) throws CatServiceException;
    CatDTO getCatById(int id);
    List<CatDTO> getAllCats();
    List<CatDTO> getAllFriends(int id);
    List<CatDTO> getCatsByBreed(String breedName);
    List<CatDTO> getCatsByColour(String colourName);
    List<CatDTO> getCatsByColourAndBreed(String colourName, String breedName);
    String makeFriends(int firstCatId, int secondCatId);
    String unfriend(int firstCatId, int secondCatId);
}

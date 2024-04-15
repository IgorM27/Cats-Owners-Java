import org.igorM.dao.CatDAOImpl;
import org.igorM.dao.OwnerDAOImpl;
import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.service.CatServiceImpl;
import org.igorM.service.OwnerServiceImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CatServiceImpl catServiceImpl = new CatServiceImpl(new CatDAOImpl(), new OwnerDAOImpl());
        OwnerServiceImpl ownerServiceImpl = new OwnerServiceImpl(new CatDAOImpl(), new OwnerDAOImpl());

        OwnerDTO owner = ownerServiceImpl.createOwner("Owner", LocalDate.of(2004, 8, 27));
        CatDTO firstCat = catServiceImpl.createCat("Cat", owner.getId(), "Burma", "White", LocalDate.of(2022, 12,1));
        CatDTO secondCat = catServiceImpl.createCat("Cat", owner.getId(), "Burma", "Grey", LocalDate.of(2022, 12,1));
        catServiceImpl.makeFriends(firstCat.getId(), secondCat.getId());
        catServiceImpl.unfriend(firstCat.getId(), secondCat.getId());
        catServiceImpl.deleteCat(firstCat.getId());
        ownerServiceImpl.deleteOwner(owner.getId());
    }
}
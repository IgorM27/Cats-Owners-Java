import org.igorM.dto.CatDTO;
import org.igorM.dto.OwnerDTO;
import org.igorM.entity.Cat;
import org.igorM.entity.Owner;
import org.igorM.models.Breed;
import org.igorM.models.Colour;
import org.igorM.repositories.CatRepository;
import org.igorM.repositories.OwnerRepository;
import org.igorM.service.CatServiceRepoImpl;
import org.igorM.service.OwnerServiceRepoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {OwnerRepository.class, CatRepository.class})
@ContextConfiguration
public class ServiceTest {
    @MockBean
    private CatRepository catRepository;
    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void getAllOwnersReturnAllOwners() {
        Owner owner1 = new Owner("Igor", LocalDate.of(2004, 8, 27), new ArrayList<>());
        Owner owner2 = new Owner("Misha", LocalDate.of(2004, 7, 3), new ArrayList<>());

        List<OwnerDTO> ownersDTO = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);

        Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(null);
        Mockito.when(ownerRepository.findAll()).thenReturn(owners);

        OwnerServiceRepoImpl service = new OwnerServiceRepoImpl(ownerRepository, catRepository);
        ownersDTO.add(service.createOwner("Igor", LocalDate.of(2004, 8, 27)));
        ownersDTO.add(service.createOwner("Misha", LocalDate.of(2004, 7, 3)));

        var result = service.getAllOwners();
        Assertions.assertEquals(ownersDTO.size(), result.size());

        Assertions.assertEquals(ownersDTO.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(ownersDTO.get(1).getId(), result.get(1).getId());

        Assertions.assertEquals(ownersDTO.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(ownersDTO.get(1).getName(), result.get(1).getName());

        Assertions.assertEquals(ownersDTO.get(0).getBirth(), result.get(0).getBirth());
        Assertions.assertEquals(ownersDTO.get(1).getBirth(), result.get(1).getBirth());
    }

    @Test
    void getAllCatsReturnAllCats() {
        Owner owner = new Owner("Igor", LocalDate.of(2004, 8, 27), new ArrayList<>());
        Cat cat1 = new Cat("Kot1", owner, Breed.Burma, Colour.Russet, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat2 = new Cat("Kot2", owner, Breed.Burma, Colour.Russet, LocalDate.of(2023, 8, 27), new ArrayList<>());


        List<CatDTO> catsDTO = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);

        Mockito.when(catRepository.save(Mockito.any(Cat.class))).thenReturn(null);
        Mockito.when(catRepository.findAll()).thenReturn(cats);
        Mockito.when(ownerRepository.getReferenceById(owner.getId())).thenReturn(owner);
        Mockito.when(ownerRepository.existsById(owner.getId())).thenReturn(true);

        CatServiceRepoImpl service = new CatServiceRepoImpl(catRepository, ownerRepository);
        catsDTO.add(service.createCat("Kot1", owner.getId(), "Burma", "Russet", LocalDate.of(2023, 12,1)));
        catsDTO.add(service.createCat("Kot2", owner.getId(), "Burma", "Russet", LocalDate.of(2023, 12,1)));

        var result = service.getAllCats();
        Assertions.assertEquals(cats.size(), result.size());

        Assertions.assertEquals(catsDTO.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(catsDTO.get(1).getId(), result.get(1).getId());

        Assertions.assertEquals(catsDTO.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(catsDTO.get(1).getName(), result.get(1).getName());
    }

    @Test
    void getCatsByColourReturnCats(){
        Owner owner = new Owner("Igor", LocalDate.of(2004, 8, 27), new ArrayList<>());
        Cat cat1 = new Cat("Kot1", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat2 = new Cat("Kot2", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat3 = new Cat("Kot3", owner, Breed.Burma, Colour.Russet, LocalDate.of(2023, 8, 27), new ArrayList<>());

        List<CatDTO> whiteCatsDTO = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);

        Mockito.when(catRepository.save(Mockito.any(Cat.class))).thenReturn(null);
        Mockito.when(catRepository.findAll()).thenReturn(cats);
        Mockito.when(ownerRepository.getReferenceById(owner.getId())).thenReturn(owner);
        Mockito.when(ownerRepository.existsById(owner.getId())).thenReturn(true);

        CatServiceRepoImpl service = new CatServiceRepoImpl(catRepository, ownerRepository);
        whiteCatsDTO.add(service.createCat("Kot1", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        whiteCatsDTO.add(service.createCat("Kot2", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        service.createCat("Kot3", owner.getId(), "Burma", "Russet", LocalDate.of(2023, 12,1));

        var result = service.getCatsByColour("White");
        Assertions.assertEquals(whiteCatsDTO.size(), result.size());

        Assertions.assertEquals(whiteCatsDTO.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(whiteCatsDTO.get(1).getId(), result.get(1).getId());

        Assertions.assertEquals(whiteCatsDTO.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(whiteCatsDTO.get(1).getName(), result.get(1).getName());
    }

    @Test
    void getCatsByBreedReturnCats(){
        Owner owner = new Owner("Igor", LocalDate.of(2004, 8, 27), new ArrayList<>());
        Cat cat1 = new Cat("Kot1", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat2 = new Cat("Kot2", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat3 = new Cat("Kot3", owner, Breed.Bengal, Colour.Russet, LocalDate.of(2023, 8, 27), new ArrayList<>());

        List<CatDTO> burmaCatsDTO = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);

        Mockito.when(catRepository.save(Mockito.any(Cat.class))).thenReturn(null);
        Mockito.when(catRepository.findAll()).thenReturn(cats);
        Mockito.when(ownerRepository.getReferenceById(owner.getId())).thenReturn(owner);
        Mockito.when(ownerRepository.existsById(owner.getId())).thenReturn(true);

        CatServiceRepoImpl service = new CatServiceRepoImpl(catRepository, ownerRepository);
        burmaCatsDTO.add(service.createCat("Kot1", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        burmaCatsDTO.add(service.createCat("Kot2", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        service.createCat("Kot3", owner.getId(), "Bengal", "Russet", LocalDate.of(2023, 12,1));

        var result = service.getCatsByBreed("Burma");
        Assertions.assertEquals(burmaCatsDTO.size(), result.size());

        Assertions.assertEquals(burmaCatsDTO.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(burmaCatsDTO.get(1).getId(), result.get(1).getId());

        Assertions.assertEquals(burmaCatsDTO.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(burmaCatsDTO.get(1).getName(), result.get(1).getName());
    }

    @Test
    void getCatsByColourAndBreedReturnCats(){
        Owner owner = new Owner("Igor", LocalDate.of(2004, 8, 27), new ArrayList<>());
        Cat cat1 = new Cat("Kot1", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat2 = new Cat("Kot2", owner, Breed.Burma, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat3 = new Cat("Kot3", owner, Breed.Burma, Colour.Russet, LocalDate.of(2023, 8, 27), new ArrayList<>());
        Cat cat4 = new Cat("Kot4", owner, Breed.Bengal, Colour.White, LocalDate.of(2023, 8, 27), new ArrayList<>());

        List<CatDTO> burmaWhiteCatsDTO = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        cats.add(cat4);

        Mockito.when(catRepository.save(Mockito.any(Cat.class))).thenReturn(null);
        Mockito.when(catRepository.findAll()).thenReturn(cats);
        Mockito.when(ownerRepository.getReferenceById(owner.getId())).thenReturn(owner);
        Mockito.when(ownerRepository.existsById(owner.getId())).thenReturn(true);

        CatServiceRepoImpl service = new CatServiceRepoImpl(catRepository, ownerRepository);
        burmaWhiteCatsDTO.add(service.createCat("Kot1", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        burmaWhiteCatsDTO.add(service.createCat("Kot2", owner.getId(), "Burma", "White", LocalDate.of(2023, 12,1)));
        service.createCat("Kot3", owner.getId(), "Burma", "Russet", LocalDate.of(2023, 12,1));
        service.createCat("Kot4", owner.getId(), "Bengal", "White", LocalDate.of(2023, 12,1));

        var result = service.getCatsByColourAndBreed("White", "Burma");
        Assertions.assertEquals(burmaWhiteCatsDTO.size(), result.size());

        Assertions.assertEquals(burmaWhiteCatsDTO.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(burmaWhiteCatsDTO.get(1).getId(), result.get(1).getId());

        Assertions.assertEquals(burmaWhiteCatsDTO.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(burmaWhiteCatsDTO.get(1).getName(), result.get(1).getName());
    }

}

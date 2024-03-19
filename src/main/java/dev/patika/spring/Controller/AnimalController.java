package dev.patika.spring.Controller;

import dev.patika.spring.Dto.Request.AnimalRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.CustomerRepo;
import dev.patika.spring.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;
    private final AnimalService animalService;
    @Autowired
    public AnimalController(AnimalRepo animalRepo, CustomerRepo customerRepo, AnimalService animalService) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
        this.animalService = animalService;
    }
    //id'ye göre hayvan bulmak için

    @GetMapping("/{id}")
    public Animal findbyId(@PathVariable("id") long id){
        return this.animalRepo.findById(id).orElseThrow();
    }

    // id yollanırsa update ediyor, id yoksa insert ediyor
    //DEĞERLENDİRME FORMU 11
    @PostMapping("/save")
    public ResponseEntity<?> saveAnimal(@RequestBody AnimalRequest animalRequest) {
        try {
            // Eğer gelen istekte id değeri yoksa yeni bir hayvan kaydedilir
            if (animalRequest.getId() == null) {
                Animal savedAnimal = animalService.saveAnimal(animalRequest);
                return ResponseEntity.ok(savedAnimal);
            } else { // Eğer id değeri varsa, id'ye göre hayvan güncellenir
                Optional<Animal> optionalAnimal = animalRepo.findById(animalRequest.getId());
                if (optionalAnimal.isPresent()) {
                    Animal existingAnimal = optionalAnimal.get();
                    // Yeni bilgilerle var olan hayvanın bilgileri güncellenir
                    existingAnimal.setName(animalRequest.getName());
                    existingAnimal.setSpecies(animalRequest.getSpecies());
                    existingAnimal.setBreed(animalRequest.getBreed());
                    existingAnimal.setGender(animalRequest.getGender());
                    existingAnimal.setColour(animalRequest.getColour());
                    existingAnimal.setDateOfBirth(animalRequest.getDateOfBirth());
                    Animal updatedAnimal = animalRepo.save(existingAnimal);
                    return ResponseEntity.ok(updatedAnimal);
                } else {
                    return ResponseEntity.notFound().build(); // Eğer id'ye sahip bir hayvan bulunamazsa 404 hatası döndürülür
                }
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAnimal(@PathVariable("id") Long id, @RequestBody AnimalRequest animalRequest) {
        try {

            // Güncellenecek hayvanı id'ye göre bul
            Optional<Animal> optionalAnimal = animalRepo.findById(id);
            Animal checkAnimal = optionalAnimal.get();

            if (!optionalAnimal.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir hayvan bulunamadı.");
            }

            // Güncellenecek hayvanı al
            Animal animalToUpdate = optionalAnimal.get();

            // Hayvanın yeni bilgilerini set et
            animalToUpdate.setName(animalRequest.getName());
            animalToUpdate.setBreed(animalRequest.getBreed());
            animalToUpdate.setGender(animalRequest.getGender());
            animalToUpdate.setSpecies(animalRequest.getSpecies());
            animalToUpdate.setDateOfBirth(animalRequest.getDateOfBirth());
            animalToUpdate.setColour(animalRequest.getColour());

            // Müşteri bilgisini kontrol et
            if (animalRequest.getCustomer() == null || animalRequest.getCustomer().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Müşteri bilgisi eksik veya geçersiz.");
            }

            if (!(animalToUpdate.getName().equals(animalRequest.getName())) || !(animalToUpdate.getCustomer().getName().equals(animalRequest.getCustomer().getName()))) {
                if (animalRepo.existsByNameAndCustomer(animalRequest.getName(), animalRequest.getCustomer())) {
                    throw new IllegalArgumentException("Bu müşteriye ait aynı isimde bir hayvan zaten var.");
                }
            }

            // Belirtilen ID'ye sahip müşterinin varlığını kontrol et
            if (!animalService.isCustomerExist(animalRequest.getCustomer().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Belirtilen ID'de bir müşteri bulunmuyor.");
            }

            // Belirtilen müşteriyi al
            Customer customer = customerRepo.findById(animalRequest.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

            // Hayvana yeni müşteriyi ata
            animalToUpdate.setCustomer(customer);

            AnimalRequest convertedAnimal = animalService.convertToAnimalRequest(animalToUpdate);

            // Güncellenmiş hayvanı kaydet
            Animal updatedAnimal = animalService.updateAnimal(id,convertedAnimal);

            return ResponseEntity.ok(updatedAnimal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




    //id ye göre hayvan silmek için
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("id") long id) {
        try {
            Optional<Animal> optionalAnimal = animalRepo.findById(id);

            if (optionalAnimal.isPresent()) {
                animalRepo.deleteById(id);
                return ResponseEntity.ok( id + " numaralı hayvan silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu id'de bir hayvan yok"); // Eğer hayvan bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Id'ye sahip hayvan silinemedi: " + id + ": " + e.getMessage());
        }
    }





    // Hayvanların listesini döndüren metot
    @GetMapping("/find-all")
    public List<Animal> findAll(){
        return this.animalRepo.findAll();
    }
    //isme göre hayvan aramak için
    //DEĞERLENDİRME FORMU 16
    @GetMapping("/name/{name}")
    public List<Animal> findByName(@PathVariable("name") String name){
        return this.animalRepo.findByAnimalNameIgnoreCase(name);
    }



}

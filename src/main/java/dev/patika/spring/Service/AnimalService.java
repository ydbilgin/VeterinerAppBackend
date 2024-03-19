package dev.patika.spring.Service;

import dev.patika.spring.Dto.Request.AnimalRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Entity.Vaccine;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.AppointmentRepo;
import dev.patika.spring.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepo animalRepository;
    private final AppointmentRepo appointmentRepo;
    private final CustomerRepo customerRepository;

    @Autowired
    public AnimalService(AnimalRepo animalRepository, AppointmentRepo appointmentRepo, CustomerRepo customerRepository) {
        this.animalRepository = animalRepository;
        this.appointmentRepo = appointmentRepo;
        this.customerRepository = customerRepository;
    }


    public Animal saveAnimal(AnimalRequest animalRequest) {
        // AnimalRequest'ten Animal'a dönüşüm yapılıyor
        Animal animal = convertToAnimal(animalRequest);
        if (animalRequest.getName() == null || animalRequest.getName().isEmpty() ||
                animalRequest.getSpecies() == null || animalRequest.getSpecies().isEmpty() ||
                animalRequest.getBreed() == null || animalRequest.getBreed().isEmpty() ||
                animalRequest.getGender() == null || animalRequest.getGender().isEmpty() ||
                animalRequest.getColour() == null || animalRequest.getColour().isEmpty() ||
                animalRequest.getDateOfBirth() == null || animalRequest.getCustomer() ==null
                || animalRequest.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz.");
        }


        // Eğer hayvanın adı ve müşterisi ile aynı isimde bir hayvan varsa hata döndür
        if (animalRepository.existsByNameAndCustomer(animalRequest.getName(), animalRequest.getCustomer())) {
            throw new IllegalArgumentException("Bu müşteriye ait aynı isimde bir hayvan zaten var.");
        }

        // Eğer Customer'ın ID'si null değilse, Customer'ın geri kalan bilgilerini getir ve set et
        if (animal.getCustomer() != null && animal.getCustomer().getId() != null) {
            Optional<Customer> optionalCustomer = customerRepository.findById(animal.getCustomer().getId());
            optionalCustomer.ifPresent(customer -> {
                animal.getCustomer().setName(customer.getName());
                animal.getCustomer().setPhone(customer.getPhone());
                animal.getCustomer().setMail(customer.getMail());
                animal.getCustomer().setAddress(customer.getAddress());
                animal.getCustomer().setCity(customer.getCity());
            });
        }

        validateAnimal(animal); // Hayvanı kontrol et

        return animalRepository.save(animal); // Hayvanı kaydet
    }

    public Animal updateAnimal(long id, AnimalRequest animalRequest) {
        // Belirtilen ID'ye sahip hayvanı al
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));

        // Güncelleme isteğindeki alanları kontrol et
        if (animalRequest.getName() == null || animalRequest.getName().isEmpty() ||
                animalRequest.getSpecies() == null || animalRequest.getSpecies().isEmpty() ||
                animalRequest.getBreed() == null || animalRequest.getBreed().isEmpty() ||
                animalRequest.getGender() == null || animalRequest.getGender().isEmpty() ||
                animalRequest.getColour() == null || animalRequest.getColour().isEmpty() ||
                animalRequest.getDateOfBirth() == null || animalRequest.getCustomer() ==null || animalRequest.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz.");
        }



        // Güncelleme isteğinde belirtilen müşteriyi al
        Customer customer = customerRepository.findById(animalRequest.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip müşteri bulunamadı."));

        // Eğer hayvanın adı ve müşterisinin adı değiştiyse ve aynı isimde hayvan varsa istisna fırlat
        if (!(animal.getName().equals(animalRequest.getName())) || !(animal.getCustomer().getName().equals(animalRequest.getCustomer().getName()))) {
            if (animalRepository.existsByNameAndCustomer(animalRequest.getName(), customer)) {
                throw new IllegalArgumentException("Bu müşteriye ait aynı isimde bir hayvan zaten var.");
            }
        }

        // Hayvanın bilgilerini güncelle
        animal.setName(animalRequest.getName());
        animal.setSpecies(animalRequest.getSpecies());
        animal.setBreed(animalRequest.getBreed());
        animal.setGender(animalRequest.getGender());
        animal.setColour(animalRequest.getColour());
        animal.setDateOfBirth(animalRequest.getDateOfBirth());
        animal.setCustomer(customer);

        // Güncellenmiş hayvanı kaydet ve geri döndür
        return animalRepository.save(animal);
    }






    public Appointment updateAppointment(Appointment appointment) {
        try {
            // Güncelleme işlemi
            return appointmentRepo.save(appointment);
        } catch (DataIntegrityViolationException e) {
            // Veritabanı kısıtlaması hatası, isteğe bağlı olarak loglama veya uygun bir şekilde işleme alabilirsiniz
            throw new RuntimeException("Güncelleme işlemi sırasında veritabanı kısıtlaması hatası oluştu.", e);
        }
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }


    public List<Animal> getAnimalsByCustomer(Customer customer) {
        return animalRepository.findByCustomer_Id(customer.getId());
    }



    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }
    public boolean isCustomerExist(Long customerId) {
        return customerRepository.existsById(customerId);
    }

    private void validateAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz!");
        }

        if (animal.getName() == null || animal.getSpecies() == null || animal.getBreed() == null ||
                animal.getGender() == null || animal.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz!" );
        }

        if (animal.getCustomer() == null || animal.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Sistemde kayıtlı bir müşteri ID'si giriniz!");
        }
    }
    public Animal convertToAnimal(AnimalRequest animalRequest) {
        Animal animal = new Animal();
        animal.setName(animalRequest.getName());
        animal.setSpecies(animalRequest.getSpecies());
        animal.setBreed(animalRequest.getBreed());
        animal.setGender(animalRequest.getGender());
        animal.setColour(animalRequest.getColour());
        animal.setDateOfBirth(animalRequest.getDateOfBirth());

        // AnimalRequest içinde bir Customer nesnesi varsa ve içinde bir ID varsa,
        // burada sadece ID'yi alıp set etmeniz gerekebilir. Örneğin:
        if (animalRequest.getCustomer() != null && animalRequest.getCustomer().getId() != null) {
            Customer customer = new Customer();
            customer.setId(animalRequest.getCustomer().getId());
            animal.setCustomer(customer);
        }

        return animal;
    }
    public AnimalRequest convertToAnimalRequest(Animal animal) {
        AnimalRequest animalRequest = new AnimalRequest();
        animalRequest.setName(animal.getName());
        animalRequest.setSpecies(animal.getSpecies());
        animalRequest.setBreed(animal.getBreed());
        animalRequest.setGender(animal.getGender());
        animalRequest.setColour(animal.getColour());
        animalRequest.setDateOfBirth(animal.getDateOfBirth());
        animalRequest.setCustomer(animal.getCustomer());


        return animalRequest;
    }


}



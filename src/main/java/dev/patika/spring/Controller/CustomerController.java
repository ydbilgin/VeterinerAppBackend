package dev.patika.spring.Controller;

import dev.patika.spring.Dto.Request.CustomerRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Repository.CustomerRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerRepo customerRepo;


    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    @GetMapping("/{id}")
    public Customer findbyId(@PathVariable("id") long id) {
        return this.customerRepo.findById(id).orElseThrow();
    }

    // id yollanırsa update ediyor, id yoksa insert ediyor

    //DEĞERLENDİRME FORMU 10
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        if (customer.getPhone() == null || customer.getPhone().isEmpty() ||
                customer.getAddress() == null ||customer.getAddress().isEmpty() ||
                customer.getName() == null ||customer.getName().isEmpty() ||
                customer.getCity() == null ||customer.getCity().isEmpty() ||
                customer.getMail() == null ||customer.getMail().isEmpty()
        ) {
            throw new IllegalArgumentException("Müşteriye ait alanlar boş olamaz.");
        }
        try {
            if (customerRepo.existsByPhone(customer.getPhone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu telefon numarasına sahip müşteri zaten mevcut.");
            }

            Customer savedCustomer = customerRepo.save(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Müşteri kaydedilemedi: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerRequest customerRequest) {
        try {
            Optional<Customer> optionalCustomer = customerRepo.findById(id);
            if (customerRequest.getPhone() == null || customerRequest.getPhone().isEmpty() ||
                    customerRequest.getAddress() == null ||customerRequest.getAddress().isEmpty() ||
                    customerRequest.getName() == null ||customerRequest.getName().isEmpty() ||
                    customerRequest.getCity() == null ||customerRequest.getCity().isEmpty() ||
                    customerRequest.getMail() == null ||customerRequest.getMail().isEmpty()
            ) {
                throw new IllegalArgumentException("Müşteriye ait alanlar boş olamaz.");
            }

            if (optionalCustomer.isPresent()) {
                Customer existingCustomer = optionalCustomer.get();
                if (!existingCustomer.getPhone().equals(customerRequest.getPhone())) {
                    // Yeni telefon numarasına sahip bir müşteri var mı diye kontrol edelim
                    if (customerRepo.existsByPhone(customerRequest.getPhone())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu telefon numarasına sahip müşteri zaten mevcut.");
                    }
                }

                existingCustomer.setName(customerRequest.getName());
                existingCustomer.setPhone(customerRequest.getPhone());
                existingCustomer.setMail(customerRequest.getMail());
                existingCustomer.setAddress(customerRequest.getAddress());
                existingCustomer.setCity(customerRequest.getCity());

                Customer savedCustomer = customerRepo.save(existingCustomer);

                return ResponseEntity.ok(savedCustomer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir müşteri bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip müşteri güncellenemedi: " + id + ": " + e.getMessage());
        }
    }




    @GetMapping("/find-all")
    public List<Customer> findAll() {
        return this.customerRepo.findAll();
    }

    //DEĞERLENDİRME FORMU 18
    @GetMapping("/{customerId}/animals")
    public List<Animal> findAnimalsByCustomerId(@PathVariable("customerId") long id) {
        Customer customer = customerRepo.findById(id).orElseThrow();

        return customer.getAnimals();
    }
    //DEĞERLENDİRME FORMU 17
    @GetMapping("/name/{name}")
    public List<Customer> findByName(@PathVariable("name") String name){
        return this.customerRepo.findByCustomerNameIgnoreCase(name);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepo.findById(id);

            if (optionalCustomer.isPresent()) {
                customerRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı müşteri silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir müşteri bulunamadı."); // Eğer müşteri bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip müşteri silinemedi: " + id + ": " + e.getMessage());
        }
    }

}

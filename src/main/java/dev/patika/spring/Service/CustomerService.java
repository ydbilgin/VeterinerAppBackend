package dev.patika.spring.Service;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepository;

    @Autowired
    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Müşteri bulunamadı: " + id));
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Animal> findAnimalsByCustomerId(long customerId) {
        Customer customer = findById(customerId);
        return customer.getAnimals();
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer customerFromDB = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " id'li müşteri veritabanında bulunamadı"));

        customerFromDB.setName(updatedCustomer.getName());
        customerFromDB.setPhone(updatedCustomer.getPhone());
        customerFromDB.setMail(updatedCustomer.getMail());
        customerFromDB.setCity(updatedCustomer.getCity());
        customerFromDB.setAddress(updatedCustomer.getAddress());


        return customerRepository.save(customerFromDB);
    }

    public List<Customer> findByCustomerName(String name) {
        return customerRepository.findByCustomerNameIgnoreCase(name);
    }

    public void deleteCustomer(long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bu ID'de bir müşteri bulunamadı: " + id);
        }
    }

}

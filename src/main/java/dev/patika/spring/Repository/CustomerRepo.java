package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findByName(String name);
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name)")
    List<Customer> findByCustomerNameIgnoreCase(@Param("name") String name);
    boolean existsByPhone(String phone);


}

package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
    List<Animal> findByName(String name);

    List<Animal> findByCustomer_Id(Long id);
    @Query("SELECT a FROM Animal a WHERE LOWER(a.name) = LOWER(:name)")
    List<Animal> findByAnimalNameIgnoreCase(@Param("name") String name);

    boolean existsByNameAndCustomer(String name, Customer customer);
}

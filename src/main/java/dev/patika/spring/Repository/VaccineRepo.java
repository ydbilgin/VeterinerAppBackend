package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    boolean existsByAnimal_IdAndNameAndCode(Long id, String name, String code);
    List<Vaccine> findByAnimal_Id(Long id);
    @Query("SELECT vaccine " +
            "FROM Vaccine vaccine " +
            "WHERE vaccine.animal.id = :id " +
            "AND vaccine.name = :name " +
            "AND vaccine.code = :code")
    List<Vaccine> findByAnimalIdAndNameAndCode(
            @Param("id") long id,
            @Param("name") String name,
            @Param("code") String code
    );

    List<Vaccine> findByAnimalIdAndNameAndCodeAndIdNot(Long animalId, String name, String code, Long id);

    @Query("SELECT DISTINCT v.animal FROM Vaccine v WHERE v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Animal> findAnimalsWithExpiringVaccines(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


}

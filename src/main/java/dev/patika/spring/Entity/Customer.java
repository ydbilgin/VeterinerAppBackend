package dev.patika.spring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table ( name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id" , columnDefinition = "serial")
    private Long id;
    @Column(name = "customer_name" , length = 100 , nullable = false)
    private String name;
    @Column(name = "customer_phone" , length = 100 , nullable = false)
    private String phone;
    @Column(name = "customer_mail" , length = 100 , nullable = false)
    private String mail;
    @Column(name = "customer_address" , length = 150 , nullable = false)
    private String address;
    @Column(name = "customer_city")
    private String city;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Animal> animals;


}

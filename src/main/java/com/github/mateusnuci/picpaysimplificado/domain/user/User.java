package com.github.mateusnuci.picpaysimplificado.domain.user;

import com.github.mateusnuci.picpaysimplificado.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(of = "id")
@Entity(name = "users")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User (UserDTO dto) {
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.document = dto.document();
        this.balance = dto.balance();
        this.email = dto.email();
        this.password = dto.password();
        this.userType = dto.userType();
    }




}

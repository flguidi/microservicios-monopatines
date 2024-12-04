package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( nullable = false )
    private String username;

    @Column( nullable = false )
    private String password;

    // Se hace una relacion many to many por si a futuro quiero agregar usuarios
    // Carga demorada siempre
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    private Set<Authority> authorities = new HashSet<>();

    public User( final String username ) {
        this.username = username.toLowerCase();
    }

    public void setAuthorities( final Collection<Authority> authorities ) {
        this.authorities = new HashSet<>( authorities );
    }

}

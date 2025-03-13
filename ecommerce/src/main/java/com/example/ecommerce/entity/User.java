package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_email",nullable = false,unique=true)
    private String email;
    @Column(name="user_password",nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",nullable = false)
    private Role role;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Cart cart;
    @Column(name = "email-confirmation",nullable = true)
    private boolean emailConfirmation;
    @Column(name = "confirmation-code",nullable = true)
    private String confirmationCode;
    public enum Role {
        ADMIN,USER
    }
    public boolean getEmailConfirmation() {
        return emailConfirmation;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

package com.caretrack.user.model;

import jakarta.persistence.CollectionTable;//create a new table name user_role to store roles
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;//user_roles table stores multiple roles for the same user.one user can be both ADmin,OPs,Maanger
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;//Adds a foreign key column inside the user_roles table.
                                     // This foreign key links the roles back to the main user record.
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;//to store more than one non repaeated value(roles) for user
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails { //UserDetails is Required by Spring Security for authentication

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 150)//used for DB column settings (length, nullable, unique)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String password;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)//It creates a new table for storing a collection of simple values ie.enum.
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))//A new table named user_roles will be created.Links each role to the user_id.
    @Enumerated(EnumType.STRING)//How enums are stored in form of string,int or char Like ADMIN
    @Column(name = "role", nullable = false, length = 30)
    private Set<Role> roles;//Role is an Enum,,Set<Role> means:The user can have multiple roles,,Duplicate roles are not allowed,Order of roles doesn’t matter
                            //eg:roles = Set.of(Role.ADMIN, Role.MANAGER); user have two roles
    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp //Automatically stored when row is created
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public User() {
    }

    public User(String email, String password, String fullName, String phone, Set<Role> roles, boolean active) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.roles = roles;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return Set.of();
        return roles.stream()
                .filter(Objects::nonNull)
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

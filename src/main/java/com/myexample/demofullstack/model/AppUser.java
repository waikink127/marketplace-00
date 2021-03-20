package com.myexample.demofullstack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "Username length not less than 5 chars and not more than 20 chars")
    @Column(unique = true)
    private String username;
    @Email(message = "Please enter valid email address")
    private String email;
    @Size(min = 8,max = 8, message = "Please enter valid phone number")
    private String phone;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 7, max = 20, message = "Password length not less than 7 chars and not more than 20 chars")
    @Transient
    private String rawPassword;
    private String password;
    private String profilePictureUrl;
    private String role;
    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private List<Product> ownedProduct;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_buyer",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> buyProduct;


    public AppUser() {
    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @PreUpdate
    public void randRawPassword(){
        setRawPassword("xxxxxxxx");
    }
    @PrePersist
    public void setRandRawPassword(){
        setRawPassword("xxxxxxxx");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Product> getOwnedProduct() {
        return ownedProduct;
    }

    public void setOwnedProduct(List<Product> ownedProduct) {
        this.ownedProduct = ownedProduct;
    }

    public List<Product> getBuyProduct() {
        return buyProduct;
    }

    public void setBuyProduct(List<Product> buyProduct) {
        this.buyProduct = buyProduct;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.enabled;
    }

    ////
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }


}

package com.example.jalatask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;



    private String firstname;


    private String lastname;

    private String email;

    @Column(name = "mobile")
    private String mobile;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String gender;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;

    private String address;

    private String country;

    private Date dob;



    @Column(name = "create_dt")
    @CreationTimestamp
    private Date createDt;

    @Column(name = "update_dt")
    @UpdateTimestamp
    private Date updateDt;

    @JsonIgnore
    @OneToMany(mappedBy="user",fetch=FetchType.EAGER)
    private Set<Authority> authorities;

    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")

    private List<String> skills;
}

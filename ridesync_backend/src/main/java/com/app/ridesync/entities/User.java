package com.app.ridesync.entities;

import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@DynamicUpdate
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String fullName;
    @Column(unique = true,nullable = false)
    private String email;
    private String address;
    private Date dateOfBirth;
    private String password;
    private String phoneNumber;
    private boolean isVerified;
}


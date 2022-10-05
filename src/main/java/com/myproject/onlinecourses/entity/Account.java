package com.myproject.onlinecourses.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "user_name")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.ALL, CascadeType.DETACH})
    private UserDetail userDetail;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Cart cart;
}

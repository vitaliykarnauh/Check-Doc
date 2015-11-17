package com.checkdoc.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "USER")
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(generator = "identity")
    @GenericGenerator(name = "identity", strategy = "identity")
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Directory> directories = new HashSet<>();

    public User() {
    }

    public User(String userName, String email, Role role) {

        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(Set<Directory> directories) {
        this.directories = directories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!getUserName().equals(user.getUserName())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        return getRole().equals(user.getRole());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getRole().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "directories=" + directories +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                '}';
    }
}

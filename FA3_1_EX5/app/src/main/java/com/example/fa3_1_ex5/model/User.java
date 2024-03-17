package com.example.fa3_1_ex5.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String username;
    private String email;
    private String password;
    private String number;
    private String isAdmin;

    public User() {
    }

    public User(String username, String email, String password, String number, String isAdmin) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setNumber(number);
        this.setIsAdmin(isAdmin);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}

    public String getNumber(){return number;}

    public void setNumber(String number){this.number = number;}

    public String getIsAdmin(){return isAdmin;}

    public void setIsAdmin(String isAdmin){this.isAdmin = isAdmin;}

    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.email);
        return hash;
    }

    //
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            User other = (User)obj;
            if (this.email != other.email) {
                return false;
            } else {
                return !Objects.equals(this.username, other.username) ? false : Objects.equals(this.id, other.id);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(this.id);
        sb.append(", username='").append(this.username).append('\'');
        sb.append(", email=").append(this.email);
        sb.append('}');
        return sb.toString();
    }
}
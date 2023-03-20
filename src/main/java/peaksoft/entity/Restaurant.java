package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
    @NoArgsConstructor
    @AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private double service;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<User> users;
    public void addUser(User user){
        if(users==null){
            users = new ArrayList<>();
        }
        users.add(user);
    }
    @OneToMany(mappedBy = "restaurant",cascade = ALL, orphanRemoval = true)
    private List<MenuItem> menuItem;



}
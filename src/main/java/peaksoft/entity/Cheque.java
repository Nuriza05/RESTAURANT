package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "cheques")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq",allocationSize = 1)
    private Long id;
    private double priceAverage;
    private LocalDate createdAt;
    @ManyToOne(cascade = {MERGE,REFRESH,PERSIST,DETACH})
    private User user;
    private double grandTotal;
    @ManyToMany(cascade = {MERGE,REFRESH,PERSIST,DETACH})
    private List<MenuItem> menuItems;
    public void addMenuItem(MenuItem menu){
        if(menuItems==null){
            menuItems = new ArrayList<>();
        }
            menuItems.add(menu);
    }




}
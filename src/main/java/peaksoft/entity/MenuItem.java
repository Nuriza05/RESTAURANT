package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;


@Getter
@Setter
@Entity
@Table(name ="menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;
    @JsonIgnore
    @ManyToMany(cascade = ALL, mappedBy = "menuItems")
    private List<Cheque> cheques;
    @ManyToOne(cascade = {MERGE, REFRESH, PERSIST, DETACH})
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem",cascade = ALL)
    private StopList stopList;
    @ManyToOne(cascade ={MERGE, REFRESH, PERSIST, DETACH} )
    private Subcategory subcategory;
     private Boolean InStock;
}
package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;


@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",cascade = ALL)
    private List<Subcategory> subcategories;
    public void addSubcategories(Subcategory subcategory){
        if(subcategories==null){
            subcategories=new ArrayList<>();
        }
        else {
            subcategories.add(subcategory);
        }
    }


}
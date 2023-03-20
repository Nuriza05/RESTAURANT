package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq",allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = {MERGE, REFRESH, PERSIST, DETACH})
    private MenuItem menuItem;

}
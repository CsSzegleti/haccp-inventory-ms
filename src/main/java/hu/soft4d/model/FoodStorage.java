package hu.soft4d.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "t_food_storage")
@Data
public class FoodStorage {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    private String id;

    @Column(length = 100)
    private String name;

    @Column(length = 3000)
    private String description;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();

    @OneToMany(mappedBy = "foodStorage")
    @JsonIgnore
    private List<Cleaning> cleanings;

    @OneToMany(mappedBy = "foodStorage")
    @JsonIgnore
    private List<ControlPoint> controlPoints;

    @OneToMany(mappedBy = "foodStorage")
    private List<InventoryItem> items;
}

package hu.soft4d.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_inventory_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryItem {

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

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @ManyToOne
    @JoinColumn(name = "food_storage_id", nullable = false)
    @JsonBackReference
    private FoodStorage foodStorage;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
}

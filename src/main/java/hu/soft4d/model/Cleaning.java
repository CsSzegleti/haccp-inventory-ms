package hu.soft4d.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_cleaning")
@Data
public class Cleaning {
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

    @ManyToOne
    @JoinColumn(name = "food_storage_id", nullable = false)
    @JsonIgnore
    private FoodStorage foodStorage;

    @Column(name = "cleaned_by", nullable = false)
    private String cleanedBy;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
}

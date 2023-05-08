package hu.soft4d.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "t_control_point")
@Data
public class ControlPoint {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    private String id;

    public enum LimitType {
        MAX,
        MIN
    }

    @Column(name = "name", nullable = false)
    @Schema(required = true)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "food_storage_id", nullable = false)
    @JsonBackReference
    private FoodStorage foodStorage;

    @OneToMany(mappedBy = "controlPoint")
    @JsonManagedReference
    private List<ControlProperty> properties;

    @Enumerated(EnumType.STRING)
    @Column(name = "limit_type", nullable = false)
    LimitType limitType;

    @Column(name = "threshold", nullable = false)
    private double threshold;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
}

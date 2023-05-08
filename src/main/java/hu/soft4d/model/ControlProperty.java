package hu.soft4d.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_control_property")
@Data
public class ControlProperty {

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

    @Column(name = "value", nullable = false)
    private double value;

    @ManyToOne
    @JoinColumn(name = "control_point_id", nullable = false)
    @JsonBackReference
    private ControlPoint controlPoint;

    @Column(name = "added_by")
    private String addedBy;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
}

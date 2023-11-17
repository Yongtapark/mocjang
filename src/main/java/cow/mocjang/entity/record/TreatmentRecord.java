package cow.mocjang.entity.record;

import cow.mocjang.entity.cow.Cow;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TreatmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cow_id")
    private Cow cow;
}

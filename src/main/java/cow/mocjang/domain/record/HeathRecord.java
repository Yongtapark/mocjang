package cow.mocjang.domain.record;

import cow.mocjang.domain.cow.Cow;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeathRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cow_id")
    private Cow cow;
    private LocalDateTime date;
    private String Symptom;
    private String note;
}

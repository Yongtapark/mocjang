package cow.mocjang.entity.record;

import cow.mocjang.entity.cow.Cow;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cow_id")
    private Cow cow;
    private LocalDateTime date;
    private String note;
}

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
public class CowDailyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cow_daily_record_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_id")
    private Cow cow;
    private LocalDateTime date;
    private String note;

    public CowDailyRecord(Cow cow, String note, LocalDateTime date) {
        this.cow = cow;
        this.note = note;
        this.date = date;
    }

    public static CowDailyRecord makeCowDailyRecord(Cow cow,String note, LocalDateTime date){
        return new CowDailyRecord(cow,note,date);
    }
}

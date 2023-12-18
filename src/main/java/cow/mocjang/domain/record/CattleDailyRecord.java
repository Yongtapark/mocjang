package cow.mocjang.domain.record;

import cow.mocjang.domain.cattles.Cattle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CattleDailyRecord implements DailyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cattle_daily_record_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cattle_id")
    private Cattle cattle;
    private LocalDateTime date;
    private String note;

    public CattleDailyRecord(Cattle cattle, String note, LocalDateTime date) {
        this.cattle = cattle;
        this.note = note;
        this.date = date;
    }

    public static CattleDailyRecord makeCowDailyRecord(Cattle cattle, String note, LocalDateTime date) {
        return new CattleDailyRecord(cattle, note, date);
    }

    @Override
    public DailyRecordDTO getDailyNote() {
        return new DailyRecordDTO(cattle.getName(), note, date);
    }
}

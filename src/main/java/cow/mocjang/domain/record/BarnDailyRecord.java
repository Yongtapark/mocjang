package cow.mocjang.domain.record;

import cow.mocjang.domain.farm.Barn;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarnDailyRecord implements DailyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barn_daily_record_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barn_id")
    private Barn barn;
    private LocalDateTime date;
    private String note;

    public BarnDailyRecord(Barn barn, String note, LocalDateTime date) {
        this.barn = barn;
        this.note = note;
        this.date = date;
    }

    public static BarnDailyRecord makeDailyRecord(Barn barn, String note, LocalDateTime date) {
        return new BarnDailyRecord(barn, note, date);
    }

    @Override
    public DailyRecordDTO getDailyNote() {
        return new DailyRecordDTO(barn.getName(), note, date);
    }
}

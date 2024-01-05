package cow.mocjang.domain.record;

import cow.mocjang.domain.cattles.Cattle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class HealthDailyRecord implements DailyRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cow_id")
    private Cattle cattle;
    private LocalDateTime date;
    private String note;

    public HealthDailyRecord(Cattle cattle, String note, LocalDateTime date) {
        this.cattle = cattle;
        this.date = date;
        this.note = note;
    }

    public static HealthDailyRecord makeHealthDailyRecord(Cattle cattle, String note, LocalDateTime date){
        return new HealthDailyRecord(cattle,note,date);
    }

    @Override
    public DailyRecordDTO getDailyNote() {
        return new DailyRecordDTO(cattle.getName(), note, date);
    }
}

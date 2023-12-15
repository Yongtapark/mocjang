package cow.mocjang.domain.record;

import cow.mocjang.domain.farm.Pen;
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
public class PenDailyRecord implements DailyRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pen_daily_record_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pen_id")
    private Pen pen;
    private LocalDateTime date;
    private String note;

    public PenDailyRecord(Pen pen, String note,LocalDateTime date) {
        this.pen = pen;
        this.note = note;
        this.date = date;
    }

    public static PenDailyRecord makePenDailyRecord(Pen pen, String note,LocalDateTime date){
        return new PenDailyRecord(pen,note,date);
    }
}

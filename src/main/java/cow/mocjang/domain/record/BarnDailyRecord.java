package cow.mocjang.domain.record;

import cow.mocjang.domain.farm.Barn;
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
public class BarnDailyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barn_daily_record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "barn_id")
    private Barn barn;
    private LocalDateTime date;
    private String note;

    public BarnDailyRecord(Barn barn, String note) {
        this.barn = barn;
        this.note = note;
    }

    public static BarnDailyRecord makeBarnDailyRecord(Barn barn, String note){
        return new BarnDailyRecord(barn,note);
    }
}

package cow.mocjang.domain.farm;

import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.record.PenDailyRecord;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Pen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pen_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barn_id")
    private Barn barn;
    private String name;
    @OneToMany(mappedBy = "pen")
    private List<Cattle> cattles;
    @OneToMany(mappedBy = "pen")
    private List<PenDailyRecord> penDailyRecords = new ArrayList<>();

    public Pen(Barn barn, String name) {
        this.barn = barn;
        this.name = name;
    }

    public static Pen makePen(Barn barn,String name){
        return new Pen(barn,name);
    }
}

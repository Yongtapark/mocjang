package cow.mocjang.domain.farm;

import cow.mocjang.domain.record.BarnDailyRecord;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Barn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barn_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;
    private String name;
    @OneToMany(mappedBy = "barn")
    private List<Pen> pens;
    @OneToMany(mappedBy = "barn")
    private List<BarnDailyRecord> barnDailyRecords = new ArrayList<>();

    public Barn(Farm farm,String name) {
        this.farm = farm;
        this.name = name;
    }

    public static Barn makeBarn(Farm farm,String barnName){
       return new Barn(farm,barnName);
    }
}

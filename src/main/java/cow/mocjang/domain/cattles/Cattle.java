package cow.mocjang.domain.cattles;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.CattleDailyRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cattle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cattle_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pen_id")
    private Pen pen;
    @Enumerated(EnumType.STRING)
    private EnCattleType cattleType;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gene_id")
    private Gene gene;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "cattle")
    private List<CattleDailyRecord> cattleDailyRecords = new ArrayList<>();
    //최근 출산일
    // 출산 기록
    // 치료 기록
    // 수정일
    // 출산 예정일

    public Cattle(Pen pen, String name, EnCattleType cattleType, Gene gene, LocalDate birthDate) {
        this.pen = pen;
        this.name = name;
        this.gene = gene;
        this.birthDate = birthDate;
        this.cattleType = cattleType;
    }

    public static Cattle makeCattle(Pen pen, String codename, EnCattleType cattleType, Gene gene, LocalDate birthDate) {
        return new Cattle(pen, codename, cattleType, gene, birthDate);
    }
}

package cow.mocjang.domain.cow;

import cow.mocjang.domain.farm.Pen;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cow_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pen_id")
    private Pen pen;
    private String codename;
    @ManyToOne
    @JoinColumn(name = "parent_cow_id")
    private Cow parrentCow;
    @ManyToOne
    @JoinColumn(name = "gene_id")
    private Gene gene;
    private LocalDate birthDate;

    public Cow(Pen pen, String codename, Cow parrentCow, Gene gene, LocalDate birthDate) {
        this.pen = pen;
        this.codename = codename;
        this.parrentCow = parrentCow;
        this.gene = gene;
        this.birthDate = birthDate;
    }

    public static Cow makeCow(Pen pen, String codename, Cow parrentCow, Gene gene, LocalDate birthDate){
        return new Cow(pen,codename,parrentCow,gene,birthDate);
    }
}

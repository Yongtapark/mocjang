package cow.mocjang.domain.cattles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Gene {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String geneticCode;
    @OneToOne
    private Cattle fatherCattle;
    @OneToOne
    private Cattle motherCattle;

    public Gene(String geneticCode, Cattle fatherCattle, Cattle motherCattle) {
        this.geneticCode = geneticCode;
        this.fatherCattle = fatherCattle;
        this.motherCattle = motherCattle;
    }
}

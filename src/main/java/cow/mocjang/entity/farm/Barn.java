package cow.mocjang.entity.farm;

import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
    @OneToMany(mappedBy = "barn")
    private List<Pen> pens;

    public Barn(Farm farm) {
        this.farm = farm;
    }

    public static Barn makeBarn(Farm farm){
       return new Barn(farm);
    }
}

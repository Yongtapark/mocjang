package cow.mocjang.domain.farm;

import cow.mocjang.domain.cow.Cow;
import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "barn_id")
    private Barn barn;
    private String name;
    @OneToMany(mappedBy = "pen")
    private List<Cow> cows;

    public Pen(Barn barn, String name) {
        this.barn = barn;
        this.name = name;
    }

    public static Pen makePen(Barn barn,String name){
        return new Pen(barn,name);
    }
}

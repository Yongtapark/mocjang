package cow.mocjang.entity.farm;

import cow.mocjang.entity.cow.Cow;
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
    @OneToMany(mappedBy = "pen")
    private List<Cow> cows;

    public Pen(Barn barn) {
        this.barn = barn;
    }

    public static Pen makePen(Barn barn){
        return new Pen(barn);
    }
}

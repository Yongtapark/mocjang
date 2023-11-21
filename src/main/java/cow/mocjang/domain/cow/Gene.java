package cow.mocjang.domain.cow;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Gene {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codename;
    @OneToOne
    private Cow fatherCow;
    @OneToOne
    private Cow motherCow;
}

package cow.mocjang.domain.farm;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id")
    private Long id;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "farm")
    private List<Member> members = new ArrayList<>();
    @OneToMany(mappedBy = "farm")
    private List<Barn> barns;
    private String tel;

    public Farm(String name, Address address, String tel) {
        this.name = name;
        this.address = address;
        this.tel = tel;
    }

    public static Farm makeFarm(String name, Address address, String tel){
        return new Farm(name,address,tel);
    }

}

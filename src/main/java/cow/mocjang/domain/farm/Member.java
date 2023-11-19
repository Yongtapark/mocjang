package cow.mocjang.domain.farm;

import cow.mocjang.domain.enums.Grade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
    private String name;
    private Grade grade;
    private String tel;

    @Embedded
    private Address address;

    public Member(Farm farm, String name, String tel, Address address) {
        this.farm = farm;
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

    public static Member makeMember(Farm farm,String name, String tel, Address address){
        return new Member(farm,name,tel,address);
    }
}

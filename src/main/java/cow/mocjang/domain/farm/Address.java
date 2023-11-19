package cow.mocjang.domain.farm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor
@Getter
public class Address {
    private String addr1;
    private String addr2;
    private String addr3;

    public Address(String addr1, String addr2, String addr3) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
    }

    public static Address makeAddress(String addr1, String addr2, String addr3) {
        return new Address(addr1, addr2, addr3);
    }
}

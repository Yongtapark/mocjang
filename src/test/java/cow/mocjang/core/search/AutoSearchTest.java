package cow.mocjang.core.search;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.enums.barns.EnBarn;
import cow.mocjang.core.enums.cattles.EnCattle;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
class AutoSearchTest {
    @Test
    @DisplayName("이름으로 검색했을때, 자동으로 축사인지, 칸인지, 소인지 검증")
    void name() {
        EnMockJang mockjangType = AutoSearch.findMockjangType("1111");

        boolean barnType = mockjangType.isSameType(EnBarn.BARN);
        assertThat(barnType).isFalse();

        boolean cattleType = mockjangType.isSameType(EnCattle.CATTLE);
        assertThat(cattleType).isTrue();
    }

}
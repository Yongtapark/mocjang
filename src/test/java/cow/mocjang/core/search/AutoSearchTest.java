package cow.mocjang.core.search;

import static cow.mocjang.core.enums.EnMockJang.*;
import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.core.enums.EnMockJang;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

        boolean barnType = mockjangType.isSameType(BARN);
        assertThat(barnType).isFalse();

        boolean cattleType = mockjangType.isSameType(CATTLE);
        assertThat(cattleType).isTrue();
    }

}
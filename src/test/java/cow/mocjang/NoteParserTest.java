package cow.mocjang;

import static cow.mocjang.core.enums.EnMockJang.BARN;
import static cow.mocjang.core.enums.EnMockJang.CATTLE;
import static cow.mocjang.core.enums.EnMockJang.PEN;
import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import cow.mocjang.core.parser.NoteParser;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
class NoteParserTest {

    @DisplayName("잘못된 값을 입력시")
    @Test
    void extractNotesByEntityWhenWrong() {
        //given
        String testInput =
                "[[s111,2222]] 밥을 먹다." + System.lineSeparator() + "[[1번축사,2번축사]] 소 판매 예정." + System.lineSeparator()
                        + "[[1-2,2-2,6-6]] 1122가 밥을 안먹음";

        Assertions.assertThatThrownBy(() -> NoteParser.sortContents(testInput)).isInstanceOf(
                IllegalNoteFormatException.class);

    }

    @DisplayName("각 값을 반환")
    @Test
    void extractNotesByEntity() {
        //given
        String testInput =
                "[[1111,2222]] 밥을 먹다." + System.lineSeparator() + "[[1번축사,5번축사]] 소 판매 예정." + System.lineSeparator()
                        + "[[1-2,2-2,6-6]] 1122가 밥을 안먹음";
        String expectedCowNote = "밥을 먹다.";
        String expectedBarnNote = "1122가 밥을 안먹음";
        String expectedPanNote = "소 판매 예정.";

        //when
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = NoteParser.sortContents(testInput);

        //then
        String actualCowNote1 = enMockJangMapMap.get(CATTLE).get("1111");
        String actualCowNote2 = enMockJangMapMap.get(CATTLE).get("2222");
        assertThat(actualCowNote1).isEqualTo(expectedCowNote);
        assertThat(actualCowNote2).isEqualTo(expectedCowNote);

        String actualBarnNote1 = enMockJangMapMap.get(PEN).get("1-2");
        assertThat(actualBarnNote1).isEqualTo(expectedBarnNote);
        String actualBarnNote2 = enMockJangMapMap.get(PEN).get("2-2");
        assertThat(actualBarnNote2).isEqualTo(expectedBarnNote);
        String actualBarnNote3 = enMockJangMapMap.get(PEN).get("6-6");
        assertThat(actualBarnNote3).isEqualTo(expectedBarnNote);

        String actualPanNote1 = enMockJangMapMap.get(BARN).get("1번축사");
        String actualPanNote2 = enMockJangMapMap.get(BARN).get("5번축사");
        assertThat(actualPanNote1).isEqualTo(expectedPanNote);
        assertThat(actualPanNote2).isEqualTo(expectedPanNote);
    }

    @DisplayName("각 값을 반환")
    @Test
    void extractMultiNotesByEntity() {
        //given
        String testInput = "[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()
                + "[[1-3]] 1132가 밥을 안먹음" + System.lineSeparator() + "[[1-2]] 1122가 밥을 안먹음";
        String expectedCowNote = "밥을 먹다.";
        String expectedBarnNote1 = "1122가 밥을 안먹음";
        String expectedBarnNote2 = "1132가 밥을 안먹음";
        String expectedPanNote = "소 판매 예정.";

        //when
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = NoteParser.sortContents(testInput);

        //then
        String actualCowNote = enMockJangMapMap.get(CATTLE).get("1111");
        assertThat(actualCowNote).isEqualTo(expectedCowNote);

        String actualBarnNote2 = enMockJangMapMap.get(PEN).get("1-3");
        assertThat(actualBarnNote2).isEqualTo(expectedBarnNote2);
        String actualBarnNote1 = enMockJangMapMap.get(PEN).get("1-2");
        assertThat(actualBarnNote1).isEqualTo(expectedBarnNote1);

        String actualPanNote = enMockJangMapMap.get(BARN).get("1번축사");
        assertThat(actualPanNote).isEqualTo(expectedPanNote);
    }

    @DisplayName("소 번호와 값을 반환")
    @Test
    void extractCowFormAndNote() {
        //given
        String id = "1111,2222";
        String value = "밥을 먹다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enCowMapMap = NoteParser.parseAndAddToMap(id, value,
                enMockJangMapMap, CATTLE);

        //then
        String actualNote1 = enCowMapMap.get(CATTLE).get("1111");
        String actualNote2 = enCowMapMap.get(CATTLE).get("2222");
        assertThat(actualNote1).isEqualTo(value);
        assertThat(actualNote2).isEqualTo(value);
    }

    @DisplayName("동일한 번호를 입력하면 예외를 발생한다.")
    @Test
    void extractCowFormAndNoteDuplicate() {
        //given
        String id = "1111,1111";
        String value = "밥을 먹다.";

        //when
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //then
        Assertions.assertThatThrownBy(() -> NoteParser.parseAndAddToMap(id, value, enMockJangMapMap,CATTLE))
                .isInstanceOf(
                        IllegalNoteFormatException.class);
    }

    @DisplayName("축사칸 번호와 값을 반환")
    @Test
    void extractBarnFormAndNote() {
        //given
        String ids = "1-1,1-2";
        String value = "문을 수리해야한다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enPenMap = NoteParser.parseAndAddToMap(ids, value, enMockJangMapMap,PEN);

        //then
        String actualNote = enPenMap.get(PEN).get("1-1");
        assertThat(actualNote).isEqualTo(value);
    }

    @DisplayName("축사 번호와 값을 반환")
    @Test
    void extractPenFormAndNote() {
        //given
        String ids = "1번축사,3번축사";
        String value = "문을 수리해야한다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enBarnMap = NoteParser.parseAndAddToMap(ids, value,
                enMockJangMapMap,BARN);

        //then
        String actualNote1 = enBarnMap.get(BARN).get("1번축사");
        assertThat(actualNote1).isEqualTo(value);
        String actualNote2 = enBarnMap.get(BARN).get("3번축사");
        assertThat(actualNote2).isEqualTo(value);
    }
}
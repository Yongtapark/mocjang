package cow.mocjang;

import static cow.mocjang.enums.pans.EnPen.PEN;
import static org.assertj.core.api.Assertions.*;

import cow.mocjang.enums.EnMockJang;
import cow.mocjang.enums.barns.EnBarn;
import cow.mocjang.enums.cows.EnCow;
import cow.mocjang.parser.BarnParser;
import cow.mocjang.parser.CowParser;
import cow.mocjang.parser.NoteParser;
import cow.mocjang.parser.PenParser;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
class NoteParserTest {

    @DisplayName("각 값을 반환")
    @Test
    void extractNotesByEntity() {
        //given
        String testInput = "[[1111,2222]] 밥을 먹다." + System.lineSeparator() + "[[1번축사,5번축사]] 소 판매 예정." + System.lineSeparator() + "[[1-2,2-2,6-6]] 1122가 밥을 안먹음";
        String expectedCowNote = "밥을 먹다.";
        String expectedBarnNote = "1122가 밥을 안먹음";
        String expectedPanNote = "소 판매 예정.";

        Map<EnMockJang, Map<String, String>> enMockJangMapMap = NoteParser.extractNotesByEntity(testInput);

        String actualCowNote1 = enMockJangMapMap.get(EnCow.COW).get("1111");
        String actualCowNote2 = enMockJangMapMap.get(EnCow.COW).get("2222");
        assertThat(actualCowNote1).isEqualTo(expectedCowNote);
        assertThat(actualCowNote2).isEqualTo(expectedCowNote);

        String actualBarnNote1 = enMockJangMapMap.get(EnBarn.BARN).get("1-2");
        assertThat(actualBarnNote1).isEqualTo(expectedBarnNote);
        String actualBarnNote2 = enMockJangMapMap.get(EnBarn.BARN).get("2-2");
        assertThat(actualBarnNote2).isEqualTo(expectedBarnNote);
        String actualBarnNote3 = enMockJangMapMap.get(EnBarn.BARN).get("6-6");
        assertThat(actualBarnNote3).isEqualTo(expectedBarnNote);

        String actualPanNote1 = enMockJangMapMap.get(PEN).get("1번축사");
        String actualPanNote2 = enMockJangMapMap.get(PEN).get("5번축사");
        assertThat(actualPanNote1).isEqualTo(expectedPanNote);
        assertThat(actualPanNote2).isEqualTo(expectedPanNote);
    }

    @DisplayName("각 값을 반환")
    @Test
    void extractMultiNotesByEntity() {
        //given
        String testInput = "[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()  + "[[1-3]] 1132가 밥을 안먹음" + System.lineSeparator() +"[[1-2]] 1122가 밥을 안먹음" ;
        String expectedCowNote = "밥을 먹다.";
        String expectedBarnNote1 = "1122가 밥을 안먹음";
        String expectedBarnNote2 = "1132가 밥을 안먹음";
        String expectedPanNote = "소 판매 예정.";

        Map<EnMockJang, Map<String, String>> enMockJangMapMap = NoteParser.extractNotesByEntity(testInput);

        String actualCowNote = enMockJangMapMap.get(EnCow.COW).get("1111");
        assertThat(actualCowNote).isEqualTo(expectedCowNote);

        String actualBarnNote2 = enMockJangMapMap.get(EnBarn.BARN).get("1-3");
        assertThat(actualBarnNote2).isEqualTo(expectedBarnNote2);
        String actualBarnNote1 = enMockJangMapMap.get(EnBarn.BARN).get("1-2");
        assertThat(actualBarnNote1).isEqualTo(expectedBarnNote1);


        String actualPanNote = enMockJangMapMap.get(PEN).get("1번축사");
        assertThat(actualPanNote).isEqualTo(expectedPanNote);
    }

    @DisplayName("번호와 값을 반환")
    @Test
    void extractCowFormAndNote() {
        //given
        String id = "1111,2222";
        String value = "밥을 먹다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enCowMapMap = CowParser.extractCowFormAndNote(id,value,enMockJangMapMap);

        //then
        String actualNote1 = enCowMapMap.get(EnCow.COW).get("1111");
        String actualNote2 = enCowMapMap.get(EnCow.COW).get("2222");
        assertThat(actualNote1).isEqualTo(value);
        assertThat(actualNote2).isEqualTo(value);
    }

    @Test
    void extractBarnFormAndNote() {
        //given
        String ids = "1-1,1-2";
        String value = "문을 수리해야한다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enBarnMap = BarnParser.extractBarnFormAndNote(ids,value,enMockJangMapMap);

        //then
        String actualNote = enBarnMap.get(EnBarn.BARN).get("1-1");
        assertThat(actualNote).isEqualTo(value);
    }

    @Test
    void extractPenFormAndNote() {
        //given
        String ids = "1번축사,3번축사";
        String value = "문을 수리해야한다.";
        Map<EnMockJang, Map<String, String>> enMockJangMapMap = new HashMap<>();

        //when
        Map<EnMockJang, Map<String, String>> enBarnMap = PenParser.extractPenFormAndNote(ids,value,enMockJangMapMap);

        //then
        String actualNote1 = enBarnMap.get(PEN).get("1번축사");
        assertThat(actualNote1).isEqualTo(value);
        String actualNote2 = enBarnMap.get(PEN).get("3번축사");
        assertThat(actualNote2).isEqualTo(value);
    }
}
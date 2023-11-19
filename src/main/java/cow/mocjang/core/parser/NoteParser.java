package cow.mocjang.core.parser;

import static cow.mocjang.core.enums.cows.EnCow.COW;
import static cow.mocjang.core.enums.pens.EnPen.PEN;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.enums.EnNoteForm;
import cow.mocjang.core.enums.barns.EnBarn;
import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoteParser {

    public static Map<EnMockJang, Map<String, String>> extractNotesByEntity(String content) {
        Map<EnMockJang, Map<String, String>> mockJangMap = new HashMap<>();
        String[] lines = content.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = EnNoteForm.NOTE_FORM.getCompile().matcher(line);
            if(matcher.find()){
                String ids = matcher.group(1);
                String value = matcher.group(2);
                boolean isCow = COW.getCompile().matcher(ids).find();
                boolean isBarn = EnBarn.BARN.getCompile().matcher(ids).find();
                boolean isPen = PEN.getCompile().matcher(ids).find();

                if (isCow) {
                    Map<EnMockJang, Map<String, String>> cowMap = CowParser.extractCowFormAndNote(ids,value, mockJangMap);
                    mockJangMap.putAll(cowMap);
                }

                if (isBarn) {
                    Map<EnMockJang, Map<String, String>> barnMap = BarnParser.extractBarnFormAndNote(ids,value,
                            mockJangMap);
                    mockJangMap.putAll(barnMap);
                }

                if (isPen) {
                    Map<EnMockJang, Map<String, String>> penMap = PenParser.extractPenFormAndNote(ids,value, mockJangMap);
                    mockJangMap.putAll(penMap);
                }

                if(!isCow && !isBarn && !isPen){
                    throw new IllegalNoteFormatException(ids);
                }
            }
        }
        return mockJangMap;
    }
}

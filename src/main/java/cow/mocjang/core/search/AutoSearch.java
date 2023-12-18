package cow.mocjang.core.search;

import static cow.mocjang.core.enums.EnMockJang.*;

import cow.mocjang.core.enums.EnMockJang;
import java.util.Arrays;

public class AutoSearch {

    public static EnMockJang findMockjangType(String name) {
        return Arrays.stream(values())
                .map(enumType->enumType.compareType(name))
                .filter(enumType -> enumType!= NONE)
                .findFirst()
                .orElse(NONE);
    }



    // 노트에서 [[]]안에 문자를 적을 때, 해당 문자열을 포함하는 모든 데이터를 리스트로 반환한다.
    // 그러려면 모든 축사,축사칸,소의 이름들을 가져와야한다.
    // 이들을 넣을 자료형은 뭐가 좋지?

    // 모든 데이터를 담았다고 가정했을 때, 어떤 자료구조가 그냥 리스트가 좋은듯. 어차피 이름만 넣을꺼면


}

package cow.mocjang.core.enums.cattles;

public enum EnCattleType {
    BULL("수소"),     // 성숙한 수소
    COW("암소"),      // 성숙한 암소
    BULL_CALF("수송아지"),     // 송아지 (성별 무관)
    HEIFER("암송아지"),   // 성적으로 성숙하지 않은 암컷 소
    STEER("거세우");     // 거세된 수컷 소

    private final String description;

    EnCattleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package cow.mocjang.domain.record;

import java.time.LocalDateTime;

public class DailyRecordDTO {
    private String name;
    private String note;
    private LocalDateTime date;

    public DailyRecordDTO(String name, String note, LocalDateTime date) {
        this.name = name;
        this.note = note;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }
}

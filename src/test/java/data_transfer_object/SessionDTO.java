package data_transfer_object;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private String session_key;
    private Long build_number;
    private LocalDateTime created_time;

    public SessionDTO(long id, String session_key, LocalDateTime created_time, long build_number) {
        this.id = id;
        this.session_key = session_key;
        this.created_time = created_time;
        this.build_number = build_number;
    }

    public String toString() {
        return "Session{" +
                "id=" + id +
                ", session_key=" + session_key +
                ", created_time=" + created_time +
                ", build_number=" + build_number +
                '}';
    }
}
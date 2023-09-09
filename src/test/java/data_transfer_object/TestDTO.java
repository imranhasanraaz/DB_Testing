package data_transfer_object;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestDTO {
    private Long id;
    private String name;
    private int status_id;
    private String method_name;
    private Long project_id;
    private Long session_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String env;
    private String browser;
    private Long author_id;

    public TestDTO(Long id, String name, int status_id, String method_name, long project_id, long session_id, LocalDateTime start_time, LocalDateTime end_time, String env, String browser, long author_id) {
        this.id = id;
        this.name = name;
        this.status_id = status_id;
        this.method_name = method_name;
        this.project_id = project_id;
        this.session_id = session_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.env = env;
        this.browser = browser;
        this.author_id = author_id;
    }

    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status_id=" + status_id +
                ", method_name='" + method_name + '\'' +
                ", project_id=" + project_id +
                ", session_id=" + session_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", env='" + env + '\'' +
                ", browser='" + browser + '\'' +
                ", author_id=" + author_id +
                '}';
    }

}
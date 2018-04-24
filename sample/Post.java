package sample;

import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post {
    private TextArea content;
    private LocalDateTime time;
    public Post(TextArea content){
        this.content = content;
        this.time = LocalDateTime.now();
    }
    public Post(TextArea content,LocalDateTime time){
        this.content = content;
        this.time = time;
    }

    public TextArea getContent() {
        return content;
    }

    public void setContent(TextArea content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

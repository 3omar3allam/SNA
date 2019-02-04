package sample;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String content;
    private LocalDateTime time;
    private User owner;
    private List<User> likers;

    public Post(String content){
        this.content = content;
        this.time = LocalDateTime.now();
        this.likers = new ArrayList<>();
    }
    public Post(String content,LocalDateTime t,int NoOfLikes,ArrayList<User> likers){
        this.content = content;
        this.time = t;
        this.likers = likers;
    }
    public Post(String content,LocalDateTime time){
        this.content = content;
        this.time = time;
        this.likers = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
        this.likers = likers;
    }


    public int getLikes() {
        return likers.size();
    }
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(this.content);
        return buffer.toString();
    }
}

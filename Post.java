package sample;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {
    private String content;
    private LocalDateTime time;
    private User owner;
    private int likes;
    private ArrayList<User> likers;

    static ArrayList<Post> allPosts;   // Vector of all posts

    public Post(String content){
        this.content = content;
        this.time = LocalDateTime.now();
        this.likes = 0;
        this.likers = new ArrayList<>();
    }
    public Post(String content,LocalDateTime time){
        this.content = content;
        this.time = time;
        this.likes = 0;
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

    public ArrayList<User> getLikers() {
        return likers;
    }

    public void setLikers(ArrayList<User> likers) {
        this.likers = likers;
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void like(){
        this.likes++;
    }
    public void unlike(){
        this.likes--;
    }

    @Override
    public String toString() {
        return this.content;
    }
}

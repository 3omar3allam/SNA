package sample;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import static sample.usefulFunctions.*;

public class Group {

    static ArrayList<Group> allGroupsID;   // Vector of all users sorted by ID
    static ArrayList<Group> allGroupsName;   // Vector of all users sorted by Name
    static int noGroups;
    private static int currentID=0;
    public static Queue<Integer> availableIDs;   //m3mlthash int 3shan tala3 error :D
    private String Name;
    private int ID;
    private ArrayList<User> Members;
    private LinkedList<Post> Posts;
    private int noMembers;
    private int noPosts;
    private User admin;
    ////////////**********Constructors**********/////////
    public Group(String name,User admin)throws Exception {
        Name = name;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=currentID;
        Members = new ArrayList<>();
        Members.add(admin);
        addToList(this);
        Posts = new LinkedList<>();
        noGroups++;
        currentID++;
        noMembers=1;
        this.admin = admin;
        this.admin.getGroups().add(this);
    }
    public Group(String name,ArrayList<User> m)throws Exception {
        Name = name;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=currentID;
        Members = m;
        addToList(this);
        noGroups++;
        noMembers=m.size();
        Posts = new LinkedList<>();
        currentID++;
        for(User user:this.Members){
            user.getGroups().add(this);
            user.setNoGroups(user.getNoGroups()+1);
        }
    }

    /////////////*******String Function used to save into csv file**********/

    @Override
    public String toString() {
        StringBuilder buffer= new StringBuilder(ID +
                "," + Name);
        for (int i = 0; i <noMembers ; i++) {
            buffer.append(",").append(Members.get(i).getID());
        }
        return buffer.toString();
    }
    //////////////******setters and getters******////////////


    public int getNoPosts() {
        return noPosts;
    }

    public void setNoPosts(int noPosts) {
        this.noPosts = noPosts;
    }
    public Post addPost(String content,User user){
        Post post = new Post(content);
        if(Posts == null) Posts = new LinkedList<>();
        Posts.add(post);
        noPosts++;
        post.setOwner(user);
        return post;
    }

    public void deletePost(Post post){
        Posts.remove(post);
        noPosts--;
    }

    public void likePost(Post post,User user){
        if(!post.getLikers().contains(this)) {
            post.getLikers().add(user);
            post.like();
        }
    }

    public void unlikePost(Post post,User user) {
        if (post.getLikers().contains(user)) {
            post.getLikers().remove(user);
            post.unlike();
        }
    }

    public LinkedList<Post> getPosts() {
        return Posts;
    }

    public void setPosts(LinkedList<Post> posts) {
        Posts = posts;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<User> getMembers() {
        return Members;
    }

    public void setMembers(ArrayList<User> members) {
        Members = members; noMembers=members.size();
    }

    public int getNoMembers() {
        return noMembers;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public  void addMember(User user)
    {

        Members.add(user);
        noMembers++;
        user.getGroups().add(this);
    }

    public void removeMember(User user)
    {
        if (Members.contains(user)) {
            Members.remove(user);
            noMembers--;
        }
        user.getGroups().remove(this);
    }

    public  boolean isMember(User user)
    {
        return Members.contains(user);
    }
    public  boolean isAdmin(User user)
    {
        return admin==user;
    }

    public void delete(){
        int id = ID;
        try{
        allGroupsID.remove(id);}
        catch(IndexOutOfBoundsException e)
        {

        }
        allGroupsName.remove(this);
        for (User member : Members) {
            member.getGroups().remove(this);
            member.setNoGroups(member.getNoGroups()-1);
        }
        Members.clear();
        Name = null;
        Members = null;
        availableIDs.add(id);
        noGroups--;
    }


}

package sample;

import java.util.List;
import java.util.Vector;

public class User {
    int ID ;   //Unique ID for each user
    String Name ;
    List<User> Friends;     //List of all friends of the user
    int noFriends;

   //******************* Constructors******************//
    public User(int ID, String name, List<User> friends, int noFriends) {

        this.ID = ID;
        Name = name;
        Friends = friends;
        this.noFriends = noFriends;
    }

    public User(String name) {
        Name = name;
        noFriends=0;
        ID=-1;

    }

    public User(int ID, String name) {
        this.ID = ID;
        Name = name;
        noFriends=0;

    }

    public User(int ID, String name, List<User> friends) {
        this.ID = ID;
        Name = name;
        noFriends=0;
        Friends = friends;
    }

    public User() {
        ID=-1;
        Name=null;
    }
 ////////////****** String Function used to save into csv file**********///////////
    @Override
    public String toString() {
        String buffer= ID + "," + Name ;

        for (int i = 0; i <noFriends ; i++) {
            buffer=buffer+','+ Friends.get(i).ID ;
        }
        return buffer;
    }
////////////****** getters and setters*************////////////
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<User> getFriends() {
        return Friends;
    }

    public void setFriends(List<User> friends) {
        Friends = friends;
    }

    public int getNoFriends() {
        return noFriends;
    }

    public void setNoFriends(int noFriends) {
        this.noFriends = noFriends;
    }
    ////////*********other functions***********//////////////
    public void addFriend(User user)
    {
        Friends.add(user);
        noFriends++;
    }
    public void deleteFriend(User user)
    {
        Friends.remove(user);
        noFriends--;
    }
    public boolean isFriend(User user)
    {
       return Friends.contains(user);

    }
}

package sample;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static sample.usefulFunctions.*;

public class User {


    public static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    public static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    private int ID ;                               //Unique ID for each user
    private static int currentID=0;
    private static Queue<Integer>  availableIDs;   //m3mlthash int 3shan tala3 error :D
    private String Name ;
    private String userName;              //Unique username for login
    private LinkedList<User> Friends;     //List of all friends of the user
    private int noFriends;

    //******************* Constructors******************//
    public User(String name,String username, LinkedList<User> friends, int noFriends) {

        Name = name;
        userName=username;
        Friends = friends;
        this.noFriends = noFriends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String name) {
        Name = name;
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}

    }

    public User(String userName, String name) {
        this.userName=userName;
        Name = name;
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String userName, String name, LinkedList<User> friends) {
        this.userName=userName;
        Name = name;
        noFriends=friends.size();
        Friends = friends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User() {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        Name=null;
        noFriends=0;
    }


    ////////////****** String Function used to save into csv file**********///////////
    @Override
    public String toString() {
        StringBuilder buffer= new StringBuilder(ID + "," + Name + "," + userName);

        for (int i = 0; i <noFriends ; i++) {
            buffer.append(',').append(Friends.get(i).ID);
        }
        return buffer.toString();
    }

    ////////////****** getters and setters*************////////////
    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String name) {
        Name = name;
    }

    public LinkedList<User> getFriends() {
        return Friends;
    }

    public void setFriends(LinkedList<User> friends) {
        Friends = friends;
        noFriends=friends.size();
    }

    public int getNoFriends() {
        return noFriends;
    }


    ////////*********other functions***********//////////////
    public void addFriend(User user)
    {
        Friends.add(user);
        noFriends++;
    }
    public void deleteFriend(User user)
    {
        if (Friends.contains(user)) {
            Friends.remove(user);
            noFriends--;
        }
    }

    public boolean isFriend(User user)
    {
        return Friends.contains(user);

    }
}
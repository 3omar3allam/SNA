package sample;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static sample.usefulFunctions.*;

public class User {

    public enum Gender{male, female}

    private int ID ;                               //Unique ID for each user
    private String userName;              //Unique username for login
    private String FirstName,LastName ;
    private Gender gender;
    private LinkedList<User> Friends;     //List of all friends of the user
    private int noFriends;
    private LocalDate birthdate;

    public static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    public static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    public static int currentID=0;
    public static Queue<Integer>  availableIDs;   //m3mlthash int 3shan tala3 error :D
    //******************* Constructors******************//
    public User(String userName,String firstName,String lastName,Gender gender,LocalDate bdate){
        this.userName = userName;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.gender = gender;
        this.birthdate = bdate;
    }

    public User(String firstName,String lastName,String username, LinkedList<User> friends, int noFriends) {
        FirstName = firstName;
        LastName = lastName;
        userName=username;
        Friends = friends;
        this.noFriends = noFriends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String userName, String firstName,String lastName) {
        this.userName=userName;
        FirstName = firstName;
        LastName = lastName;
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String userName, String firstName, String lastName, LinkedList<User> friends) {
        this.userName=userName;
        FirstName = firstName;
        LastName = lastName;
        noFriends=friends.size();
        Friends = friends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User() {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        FirstName = null;
        LastName = null;
        noFriends=0;
    }


    ////////////****** String Function used to save into csv file**********///////////
    @Override
    public String toString() {
        StringBuilder buffer= new StringBuilder(ID + "," + getName() + "," + userName);

        for (int i = 0; i <noFriends ; i++) {
            buffer.append(',').append(Friends.get(i).ID);
        }
        return buffer.toString();
    }

    ////////////****** getters and setters*************////////////
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LinkedList<User> getFriends() {
        return Friends;
    }

    public void setFriends(LinkedList<User> friends) {
        Friends = friends;
    }

    public int getNoFriends() {
        return noFriends;
    }

    public void setNoFriends(int noFriends) {
        this.noFriends = noFriends;
    }

    public String getName(){
        return FirstName+" "+LastName;
    }

    public int getAge(){
        return Period.between(LocalDate.now(),birthdate).getYears();
    }

    public void setName(String name) throws Exception {
        StringTokenizer strtok = new StringTokenizer(name);
        try {
            FirstName = strtok.nextToken(" ");
            LastName = strtok.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid Name");
        }
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
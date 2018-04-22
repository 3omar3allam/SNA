package sample;


import java.io.BufferedReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Date;
import java.util.StringTokenizer;

import static sample.usefulFunctions.*;
enum gender{male,female}

public class User {


    public static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    public static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    private int ID ;                               //Unique ID for each user
    private gender Gender;
    private LocalDate birthDate;
    public static int currentID=0;
    public static Queue<Integer>  availableIDs;  //m3mlthash int 3shan tala3 error :D
    // ^^ wenta btinitializeha mat3melsh = new Queue<> .. e3mel = new ArrayList<>() aw new LinkedList<>()  <allam>
    private String FirstName;
    private String LastName;
    private String UserName;              //Unique username for login
    private LinkedList<User> Friends;     //List of all friends of the user
    private int noFriends;

    //******************* Constructors******************//
    public User(String userName,String firstName,String lastName, String gender, LocalDate birthDate)throws Exception {
        FirstName = firstName;
        LastName = lastName;
        UserName = userName;
        this.noFriends = noFriends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        this.birthDate = LocalDate.of(birthDate.getYear(),birthDate.getMonth(),birthDate.getDayOfMonth());
        if(gender.equals("male"))  Gender = sample.gender.male;
        else Gender = sample.gender.female;
        addToList(this); //implemented in usefulFunctions class
    }

    public User(String name) throws Exception {
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        addToList(this);
    }

    public User(String userName, String firstName,String lastName)throws Exception{
        this.UserName=userName;
        FirstName = firstName;
        LastName = lastName;
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        addToList(this);
    }

    public User(String userName, String name) throws Exception {
        this.UserName=userName;
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        addToList(this);
    }

    public User() throws Exception {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        FirstName=null;
        LastName=null;
        noFriends=0;
        addToList(this);
    }

    //*******************Copy Constructor******************//
    public User(User copyUser) throws Exception
    {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        FirstName=copyUser.getFirstName();
        LastName=copyUser.getLastName();
        Friends=copyUser.getFriends();
        noFriends=Friends.size();
        UserName=copyUser.getUserName();
        addToList(this);
    }


    ///////////////////////////////////////////////////////

    @Override
    public String toString(){
        StringBuilder buffer= new StringBuilder(ID + "," + UserName + "," + FirstName + "," + LastName);
        for (int i = 0; i <noFriends ; i++) {
            buffer.append(',').append(Friends.get(i).ID);
        }
        return buffer.toString();
    }

    ////////////****** getters and setters*************////////////
    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }

    public String getName(){
        return FirstName + " " + LastName;
    }

    public void setFirstName(String name) {
        FirstName = name;
    }
    public void setLastName(String name){
        LastName = name;
    }

    public void setName(String name) throws Exception {
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid Name");
        }
    }

    public void setUserName(String name) {
        UserName = name;
    }

    public String getUserName() {
        return UserName;
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
    public gender getGender(){return Gender;}
    public void setGender(gender g){Gender=g;}
    public LocalDate getBirthDate(){return birthDate;}
    public void setBirthDate(LocalDate d){birthDate=d;}
    public int getAge(){
        return Period.between(LocalDate.now(),birthDate).getYears();
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

    public void delete()
    {
        FirstName=null;
        LastName=null;
        UserName=null;
        noFriends=0;
        Friends=null;
        Gender=null;
        birthDate=null;
        allUsersID.remove(this);
        allUsersName.remove(this);
    }

}
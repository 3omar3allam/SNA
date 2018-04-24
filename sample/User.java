package sample;


import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.naming.Name;
import java.awt.*;
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


    static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    static int noUsers;

    private int ID ;                               //Unique ID for each user
    private gender Gender;
    private LocalDate birthDate;
    static int newID=0;
    static Queue<Integer>  availableIDs;  //m3mlthash int 3shan tala3 error :D
    // ^^ wenta btinitializeha mat3melsh = new Queue<> .. e3mel = new ArrayList<>() aw new LinkedList<>()  <allam>
    private String FirstName;
    private String LastName;
    private String UserName;              //Unique username for login
    private LinkedList<User> Friends;     //List of all friends of the user
    private int noFriends;
    private int noPosts;
    private LinkedList<Post> Posts;

    //******************* Constructors******************//
    public User(String userName,String firstName,String lastName, String gender, LocalDate birthDate)throws Exception {
        if(!firstName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("first name"));
        if(!lastName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("last name"));
        FirstName = firstName;
        LastName = lastName;
        if(!userName.matches("[\\w]+")) throw new UsernameException("username should contain only word characters");
        UserName = userName;
        this.noFriends = 0;
        this.noPosts = 0;
        this.Posts = new LinkedList<>();
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        this.birthDate = LocalDate.of(birthDate.getYear(),birthDate.getMonth(),birthDate.getDayOfMonth());
        if(this.getAge()<8) throw new AgeException("8 years is the minimum age allowed");
        if(gender.equals("male"))  Gender = sample.gender.male;
        else Gender = sample.gender.female;
        addToList(this); //implemented in usefulFunctions class
        newID++;
        noUsers++;
    }

    public User(String userName, String firstName,String lastName)throws Exception{
        this.UserName=userName;
        if(!firstName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("first name"));
        if(!lastName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("last name"));
        FirstName = firstName;
        LastName = lastName;
        noFriends=0;
        noPosts = 0;
        this.Posts = new LinkedList<>();
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        addToList(this);
        newID++;
        noUsers++;
    }

    public User() throws Exception {  //da by3mel eh ? :D <allam>
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        FirstName=null;
        LastName=null;
        noFriends=0;
        noPosts = 0;
        this.Posts = new LinkedList<>();
        addToList(this);
        newID++;
        noUsers++;
    }

    //*******************Copy Constructor******************//
    public User(User copyUser) throws Exception //w da kaman by3mel eh ?? :D
    {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        FirstName=copyUser.getFirstName();
        LastName=copyUser.getLastName();
        Friends=copyUser.getFriends();
        noFriends=copyUser.noFriends;
        noPosts = copyUser.noPosts;
        this.Posts.addAll(copyUser.Posts);
        UserName=copyUser.getUserName();
        addToList(this);
        newID++;
        noUsers++;
    }


    ///////////////////////////////////////////////////////

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder(ID + "," + UserName + "," + FirstName + "," + LastName);
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
        return Period.between(birthDate,LocalDate.now()).getYears();
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
    public void addPost(TextArea content){
        Post post = new Post(content);
        if(Posts == null) Posts = new LinkedList<>();
        Posts.add(post);
    }

    public void setNoFriends(int noFriends) {
        this.noFriends = noFriends;
    }

    public int getNoPosts() {
        return noPosts;
    }

    public void setNoPosts(int noPosts) {
        this.noPosts = noPosts;
    }

    public LinkedList<Post> getPosts() {
        return Posts;
    }

    public boolean isFriend(User user)
    {
        return Friends.contains(user);
    }

    public void delete()
    {
        int id = ID;
        allUsersID.remove(id);
        allUsersName.remove(this);
        availableIDs.add(id);
        FirstName=null;
        LastName=null;
        UserName=null;
        Friends=null;
        Gender=null;
        birthDate=null;
        Posts.clear();
        Posts = null;
        noUsers--;
    }
}
package sample;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static sample.usefulFunctions.addToList;
import static sample.usefulFunctions.name_index;
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
    private ArrayList<User> Friends;     //List of all friends of the user
    private int noFriends;
    private int noGroups;
    private int noPosts;
    private LinkedList<Post> Posts;
    private LinkedList<Group> Groups;

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
        this.noGroups = 0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        this.birthDate = LocalDate.of(birthDate.getYear(),birthDate.getMonth(),birthDate.getDayOfMonth());
        if(this.getAge()<8) throw new AgeException("8 years is the minimum age allowed");
        if(gender.equals("male"))  Gender = sample.gender.male;
        else Gender = sample.gender.female;
        addToList(this); //implemented in usefulFunctions class
        Posts = new LinkedList<>();
        Friends = new ArrayList<>();
        Groups = new LinkedList<>();
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
        noGroups = 0;
        addToList(this);
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        newID++;
        noUsers++;
        Posts = new LinkedList<>();
        Friends = new ArrayList<>();
        Groups = new LinkedList<>();
    }

    //*******************Copy Constructor******************//
    public User(User copyUser) throws Exception //w da kaman by3mel eh ?? :D
    {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        UserName=copyUser.getUserName();
        FirstName=copyUser.getFirstName();
        LastName=copyUser.getLastName();
        noFriends=copyUser.getNoFriends();
        noPosts = copyUser.getNoPosts();
        noGroups = copyUser.getNoGroups();
        addToList(this);
        Posts = new LinkedList<>(copyUser.getPosts());
        Friends = new ArrayList<>(copyUser.getFriends());
        Groups = new LinkedList<>(copyUser.getGroups());
        newID++;
        noUsers++;
    }


    ///////////////////////////////////////////////////////

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder("\""+UserName+"\"");

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

    public ArrayList<User> getFriends() {
        return Friends;
    }

    public void setFriends(ArrayList<User> friends) {
        Friends = friends;
        noFriends=friends.size();
    }

    public int getNoFriends() {
        return noFriends;
    }
    public gender getGender(){return Gender;}
    public String getGender_string(){
        if(this.Gender == gender.male) return "Male";
        else return "Female";
    }
    public void setGender(gender g){Gender=g;}
    public LocalDate getBirthDate(){return birthDate;}
    public void setBirthDate(LocalDate d){birthDate=d;}
    public int getAge(){
        return Period.between(birthDate,LocalDate.now()).getYears();
    }
    ////////*********other functions***********//////////////

    public void addFriend(User user) throws Exception {
        if(!this.Friends.contains(user)) {
            int index = name_index(Friends,0,Friends.size(),user);
            Friends.add(index,user);
            noFriends++;
            index = name_index(user.Friends,0,user.Friends.size(),user);
            user.getFriends().add(index,this);
            user.setNoFriends(user.getNoFriends()+1);
        }
    }
    public void deleteFriend(User user)
    {
        if (Friends.contains(user)) {
            Friends.remove(user);
            noFriends--;
            user.getFriends().remove(this);
            user.setNoFriends(user.getNoFriends()-1);
        }
    }
    public Post addPost(String content){
        Post post = new Post(content);
        if(Posts == null) Posts = new LinkedList<>();
        Posts.add(post);
        noPosts++;
        post.setOwner(this);
        return post;
    }

    public void  addPost(Post p){

        Posts.add(p);
    }


    public void deletePost(Post post){
        Posts.remove(post);
        noPosts--;
    }

    public void likePost(Post post){
        if(!post.getLikers().contains(this)) {
            post.getLikers().add(this);
            post.like();
        }
    }

    public void unlikePost(Post post) {
        if (post.getLikers().contains(this)) {
            post.getLikers().remove(this);
            post.unlike();
        }
    }


    public int getNoGroups() {
        return noGroups;
    }

    public void setNoGroups(int noGroups) {
        this.noGroups = noGroups;
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

    public LinkedList<Group> getGroups() {
        return Groups;
    }

    public void setGroups(LinkedList<Group> groups) {
        Groups = groups;
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
        Gender=null;
        birthDate=null;
        Posts.clear();
        Posts = null;
        noUsers--;
        for(User friend : Friends) this.deleteFriend(friend);
        Friends = null;
    }
}
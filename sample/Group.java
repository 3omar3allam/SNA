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
    static Label lbl_groups = new Label();

    private static int currentID=0;
    public static Queue<Integer> availableIDs;   //m3mlthash int 3shan tala3 error :D
    private String Name;
    private int ID;
    private LinkedList<User> Members;
    private int noMembers;

    ////////////**********Constructors**********/////////
    public Group(String name,User admin)throws Exception {
        Name = name;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=currentID;
        Members = new LinkedList<>();
        Members.add(admin);
        addToList(this);
        noGroups++;
        currentID++;
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


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public List<User> getMembers() {
        return Members;
    }

    public void setMembers(LinkedList<User> members) {
        Members = members; noMembers=members.size();
    }

    public int getNoMembers() {
        return noMembers;
    }

    public  void addMember(User user)
    {
        Members.add(user);
        noMembers++;
    }

    public void removeMember(User user)
    {
        if (Members.contains(user)) {
            Members.remove(user);
            noMembers--;
        }
    }

    public  boolean isMember(User user)
    {
        return Members.contains(user);
    }

    public void delete(){
        allGroupsID.remove(ID);
        allGroupsName.remove(this);
        Members.clear();
        Name = null;
        Members = null;
        availableIDs.add(ID);
        noGroups--;
    }


}

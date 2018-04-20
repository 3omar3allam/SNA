package sample;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Group {

    private int currentID=0;
    private static Queue<Integer> availableIDs;   //m3mlthash int 3shan tala3 error :D
    private String Name;
    private int ID;
    private LinkedList<User> Members;
    private int noMembers;


    ////////////**********Constructors**********/////////
    public Group(String name, LinkedList<User> members) {
        Name = name;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        Members = members;
        this.noMembers = members.size();
    }

    public Group()
    {
        Name = null;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        noMembers = 0;
    }

    public Group(String name) {
        Name = name;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
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

    public  void deleteMember(User user)
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

}
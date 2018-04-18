package sample;

import java.util.List;
import java.util.Vector;

public class Group {
    String Name;
    int ID;
    List<User> Members;
    int noMembers;
////////////**********Constructors**********/////////
    public Group(String name, int ID, Vector<User> members, int noMembers) {
        Name = name;
        this.ID = ID;
        Members = members;
        this.noMembers = noMembers;
    }

    public Group() {
        Name = null;
        ID = -1;
        noMembers = 0;
    }

    public Group(String name, int ID) {
        Name = name;
        this.ID = ID;
    }

    public Group(String name) {
        Name = name;
        ID=-1;
    }
    /////////////*******String Function used to save into csv file**********/

    @Override
    public String toString() {
        String buffer= ID +
                "," + Name ;
        for (int i = 0; i <noMembers ; i++) {
            buffer=buffer+","+Members.get(i).ID;
        }
        return buffer;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<User> getMembers() {
        return Members;
    }

    public void setMembers(List<User> members) {
        Members = members;
    }

    public int getNoMembers() {
        return noMembers;
    }

    public void setNoMembers(int noMembers) {
        this.noMembers = noMembers;
    }
    ///////////*********otherFunctions********///////////
    public  void addMember(User user)
    {

        Members.add(user);
        noMembers++;
    }
    public  void deleteMember(User user)
    {

        Members.remove(user);
        noMembers--;
    }
    public  boolean isMember(User user)
    {
      return Members.contains(user);

    }
}

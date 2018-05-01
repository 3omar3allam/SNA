package sample;

import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

import static sample.User.allUsersID;
import static sample.User.allUsersName;
import static sample.Group.allGroupsID;
import static sample.Group.allGroupsName;

public class usefulFunctions {

    static int userNameBinarySearch(ArrayList<User> arr, int start, int end, String name){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        int compare = name.compareTo(arr.get(mid).getUserName());
        if(compare == 0) return mid;
        else if(compare < 0) return userNameBinarySearch(arr,start,mid,name);
        else return userNameBinarySearch(arr,mid+1,end,name);
    }
    static int userName_index(ArrayList<User> arr, int start, int end, String username) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        int compare = username.compareTo(arr.get(mid).getUserName());
        if (compare == 0) return -1;
        else if (compare < 0) return userName_index(arr, start, mid, username);
        else return userName_index(arr, mid + 1, end, username);
    }

    static int userIDBinarySearch(ArrayList<User> arr, int start, int end, int ID){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        if(ID == arr.get(mid).getID()) return mid;
        else if(ID < arr.get(mid).getID()) return userIDBinarySearch(arr,start,mid,ID);
        else return userIDBinarySearch(arr,mid+1,end,ID);
    }

    static int userID_index(ArrayList<User> arr, int start, int end, int ID) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        if (ID == arr.get(mid).getID()) return -1;
        else if (ID < arr.get(mid).getID()) return userID_index(arr, start, mid, ID);
        else return userID_index(arr, mid + 1, end, ID);
    }



    static int groupNameBinarySearch(ArrayList<Group> arr, int start, int end, String name){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        int compare = name.compareTo(arr.get(mid).getName());
        if(compare == 0) return mid;
        else if(compare < 0) return groupNameBinarySearch(arr,start,mid,name);
        else return groupNameBinarySearch(arr,mid+1,end,name);
    }
    static int groupName_index(ArrayList<Group> arr, int start, int end, String username) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        int compare = username.compareTo(arr.get(mid).getName());
        if (compare == 0) return -1;
        else if (compare < 0) return groupName_index(arr, start, mid, username);
        else return groupName_index(arr, mid + 1, end, username);
    }

    static int groupIDBinarySearch(ArrayList<Group> arr, int start, int end, int ID){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        if(ID == arr.get(mid).getID()) return mid;
        else if(ID < arr.get(mid).getID()) return groupIDBinarySearch(arr,start,mid,ID);
        else return groupIDBinarySearch(arr,mid+1,end,ID);
    }

    static int groupID_index(ArrayList<Group> arr, int start, int end, int ID) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        if (ID == arr.get(mid).getID()) return -1;
        else if (ID < arr.get(mid).getID()) return groupID_index(arr, start, mid, ID);
        else return groupID_index(arr, mid + 1, end, ID);
    }

    /* for duplicate allowed lists */
    static int name_index(ArrayList<User> arr, int start, int end, User user) throws Exception {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        int username_compare = user.getUserName().compareTo(arr.get(mid).getUserName());
        if(username_compare == 0)throw new NameException("User is already a member",null);
        int name_compare = user.getName().compareTo(arr.get(mid).getName());
        if (name_compare == 0) return mid;
        else if (name_compare < 0) return name_index(arr, start, mid, user);
        else return name_index(arr, mid + 1, end, user);
    }

    static void addToList(User user)throws Exception {
        int index;
        String username = user.getUserName();
        index = userName_index(allUsersName,0,allUsersName.size(),username);
        if(index==-1) throw new UsernameException("username already taken");
        else allUsersName.add(index, user);
        int id=user.getID();
        index= userID_index(allUsersID,0,allUsersID.size(),id);
        if(index==-1); //userID duplication (impossible)
        else allUsersID.add(index, user);
    }

    static void addToList(Group group) throws Exception {
        int index;
        String name = group.getName();
        index = groupName_index(allGroupsName,0,allGroupsName.size(),name);
        if(index==-1) throw new GroupNameException("group name already exists");
        else allGroupsName.add(index, group);
        int id=group.getID();
        index= groupID_index(allGroupsID,0,allGroupsID.size(),id);
        if(index==-1); //groupID duplication (impossible)
        else allGroupsID.add(index, group);
    }

    public static void user_graph_search(User source, ObservableList<User> matching_names, String name){ //add the results to the observable list
        //law 3ayz tezawed arguments e3mel interfacing w endah function tanya mattala3sh den omy :D
        //feel free enak traga3ha fadya law mafesh results .. mesh hyhsal errors

        //khaly el code da el condition bta3ak .. 3ashan di matching wana basearch mesh shart ykon el esmo kollo
        /*
        int lengh = name.length();
        if( (length <= found_first_name_in_search && name == found_first_name_in_search.substring(0,length))
            || (length <= found_last_name_in_search && name == found_first_name_in_search.substring(0,length)) )
                <yeb2a hoto fel list>

         */


        /*da code testing eb2a shelo*/
        matching_names.addAll(allUsersName);
    }


    public static void group_graph_search(User source, ObservableList<Group> matching_names, String name){ //add the results to the observable list
        /*da code testing eb2a shelo*/
        matching_names.addAll(allGroupsName);
    }

    public static void get_friends_recommendations(User source, ArrayList<User> recommended_friends, ArrayList<Integer> mutual_friends){
        //3ayz 2osad kol user fel list el 2ola be nafs el index el mutual friends fel list el tanya :D
        //law mafesh mutual raga3 zero ew3a ttlakhbat fel indeces wenaby ana mesh mehandelha :D

        /*da code testing eb2a shelo*/
        recommended_friends.addAll(allUsersName);
        while(mutual_friends.size() < recommended_friends.size())mutual_friends.add(0);
    }
    public static void get_groups_recommendations(User source, ArrayList<Group> recommended_groups, ArrayList<Integer> mutual_friends){
        //nafs el kalam b2a
        recommended_groups.addAll(allGroupsName);
        while(mutual_friends.size() < recommended_groups.size())mutual_friends.add(0);
    }
    public static void search_group_members(Group group, ObservableList<User> Foundmembers, String partName) {
        int size=partName.length();
        Foundmembers.clear();
        for(User member: group.getMembers())
        {
            if(member.getName().length()>=size) {
                if (member.getName().substring(0, size ).equals(partName)) {
                    if(!group.isAdmin(member))Foundmembers.add(member);
                    continue;
                }
            }
            if(member.getLastName().length()>=size)
                if(member.getLastName().substring(0, size).equals(partName))
            {
                if(!group.isAdmin(member))Foundmembers.add(member);
            }
        }

    }
    public static void search_user_friends(User user, ObservableList<User> Foundmembers, String partName,Group group) {
        int size=partName.length();
        Foundmembers.clear();
        for(User member: user.getFriends())
        {
            if(member.getName().length()>=size) {
                if (member.getName().substring(0, size ).equals(partName)) {
                    if(!group.isMember(member)){Foundmembers.add(member);
                    continue;}
                }
            }
            if(member.getLastName().length()>=size)
                if(member.getLastName().substring(0, size).equals(partName))
                {
                    if(!group.isMember(member)){Foundmembers.add(member);}
                }
        }

    }


}












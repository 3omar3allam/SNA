package sample;

import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.util.*;

import static sample.User.allUsersID;
import static sample.User.allUsersName;
import static sample.Group.allGroupsID;
import static sample.Group.allGroupsName;

public class usefulFunctions {

    static int no_index(ArrayList<Integer> arr, int start, int end, int number){
        if(start >= end) return start;
        int mid = (start + end - 1)/2;
        if(arr.get(mid)==number) return -1;
        else if(arr.get(mid) < number) return no_index(arr,start,mid,number);
        else return no_index(arr,mid+1,end,number);
    }

    static int noBinarySearch(ArrayList<Integer> arr, int start, int end, int number){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        if(arr.get(mid)==number) return mid;
        else if(arr.get(mid) < number) return noBinarySearch(arr,start,mid,number);
        else return noBinarySearch(arr,mid+1,end,number);
    }

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

    public static void user_graph_search(User source, ObservableList<User> matching_names,ArrayList<Integer>mutualFrineds, String name){
        int length=name.length();
        ArrayList<User> friendsArray1=new ArrayList<>();
        ArrayList<Integer>mutualFriendsNo1=new ArrayList<>();
        ArrayList<User> friendsArray2=new ArrayList<>();
        ArrayList<Integer>mutualFriendsNo2=new ArrayList<>();
        for (User user: source.getFriends())
        {
            if ((length <= user.getName().length() && user.getName().toLowerCase().substring(0, length).equals(name.toLowerCase()))
                    || (length <= user.getLastName().length() && user.getLastName().toLowerCase().substring(0, length).equals(name.toLowerCase())))
            {
                if (!friendsArray1.contains(user)&& !friendsArray2.contains(user))
                {
                    if(friendsArray1.isEmpty()) {
                        friendsArray1.add(user);
                        int index = source.getFriends().indexOf(user);
                        mutualFriendsNo1.add(source.getMutualFriendsNo().get(index));
                    }
                    else{
                        int index = source.getFriends().indexOf(user);
                        int addIndex=noBinarySearch(mutualFriendsNo1,0,mutualFriendsNo1.size(),source.getMutualFriendsNo().get(index));
                        if(addIndex==-1)addIndex=no_index(mutualFriendsNo1,0,mutualFriendsNo1.size(),source.getMutualFriendsNo().get(index));
                        friendsArray1.add(addIndex,user);
                        mutualFriendsNo1.add(addIndex,source.getMutualFriendsNo().get(index));
                    }
                }
            }
        }
        for (User user : allUsersName) {
            if ((length <= user.getName().length() && user.getName().toLowerCase().substring(0, length).equals(name.toLowerCase()))
                    || (length <= user.getLastName().length() && user.getLastName().toLowerCase().substring(0, length).equals(name.toLowerCase())))
            {
                if (!friendsArray1.contains(user)&& !friendsArray2.contains(user))
                {
                    if(friendsArray2.isEmpty()) {
                        friendsArray2.add(user);
                        mutualFriendsNo2.add(MutualFriendsNo(source,user));
                    }
                    else {
                        int MutualFriendsNo=MutualFriendsNo(source,user);
                        int addIndex=noBinarySearch(mutualFriendsNo2,0,mutualFriendsNo2.size(),MutualFriendsNo);
                        if(addIndex==-1)addIndex=no_index(mutualFriendsNo2,0,mutualFriendsNo2.size(),MutualFriendsNo);
                        friendsArray2.add(addIndex,user);
                        mutualFriendsNo2.add(addIndex,MutualFriendsNo);
                    }
                }
            }
        }
        Collections.reverse(friendsArray1);Collections.reverse(friendsArray2);
        Collections.reverse(mutualFriendsNo1);Collections.reverse(mutualFriendsNo2);
        matching_names.addAll(friendsArray1);
        matching_names.addAll(friendsArray2);
        mutualFrineds.addAll(mutualFriendsNo1);
        mutualFrineds.addAll(mutualFriendsNo2);
    }



    public static void group_graph_search(User source, ObservableList<Group> matching_names, String name){
        //we check the user's groups, then we check his friends' groups, then friends' of friends' groups, then the whole database
        /*
        int length=name.length();
        Queue<User> userQueue=new LinkedList<>(source.getFriends());
        Queue<Group> groupQueue=new LinkedList<>(source.getGroups());
        int size=groupQueue.size();
        for (int i = 0; i < size; i++)
        {
            Group current = groupQueue.poll();
            if (current.getName().length() >= length && current.getName().substring(0, length).toLowerCase().equals(name.toLowerCase()))
                if(!matching_names.contains(current))matching_names.add(current);
        }
        if(matching_names.isEmpty()) {//no users with close connections found, search all users
            for (User user : allUsersName) {
                if ((length <= user.getName().length() && user.getName().toLowerCase().substring(0, length).equals(name.toLowerCase()))
                        || (length <= user.getLastName().length() && user.getLastName().toLowerCase().substring(0, length).equals(name.toLowerCase())))
                    if (!matching_names.contains(user)) matching_names.add(user);
            }
        }*/
    }

    public static int MutualFriendsNo(User user1,User user2)
    {
        int number=0;
        for(User u:user1.getFriends())
            if(user2.getFriends().contains(u)&& u!=user2)number++;
        return number;
    }

    public static void InitMutualFriendsList(User user)
    {
        ArrayList<User> friends=user.getFriends();
        ArrayList<Integer> mutualFriendsCounter=new ArrayList<>();
        for(User user2:friends)
        {
            int noMutualFriends=MutualFriendsNo(user,user2);
            mutualFriendsCounter.add(noMutualFriends);
        }
        user.setMutualFriendsNo(mutualFriendsCounter);
    }


    public static void get_friends_recommendations(User source, ArrayList<User> recommended_friends, ArrayList<Integer> mutual_friends){

        

    }


    public static void get_groups_recommendations(User source, ArrayList<Group> recommended_groups, ArrayList<Integer> mutual_friends){
        //nafs el kalam b2a
        recommended_groups.addAll(allGroupsName);
        while(mutual_friends.size() < recommended_groups.size())mutual_friends.add(0);
    }


    public static void search_group_members(User user,Group group, ObservableList<User> Foundmembers, String partName) {
        int size=partName.length();
        Foundmembers.clear();
        for(User member: group.getMembers())
        {
            if(member.getName().length()>=size && member.getName().substring(0, size ).toLowerCase().equals(partName.toLowerCase())) {
                    if(member == user)continue;
                    Foundmembers.add(member);
            }
            else if(member.getLastName().length()>=size && member.getLastName().substring(0, size).toLowerCase().equals(partName.toLowerCase()))
            {
                if(member == user)continue;
                Foundmembers.add(member);
            }
        }
    }
    public static void search_user_friends(User user, ObservableList<User> Foundmembers, String partName,Group group) {
        int size=partName.length();
        Foundmembers.clear();
        for(User member: user.getFriends())
        {
            if(member.getName().length()>=size && member.getName().substring(0, size).toLowerCase().equals(partName.toLowerCase())) {
                if(!group.isMember(member))Foundmembers.add(member);
            }
            else if(member.getLastName().length()>=size && member.getLastName().substring(0, size).toLowerCase().equals(partName.toLowerCase()))
                {
                    if(!group.isMember(member)){Foundmembers.add(member);}
                }
        }
    }


}












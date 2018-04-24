package sample;

import java.util.ArrayList;

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
        index = groupName_index(allGroupsName,0,allUsersName.size(),name);
        if(index==-1) throw new GroupNameException("group name already exists");
        else allGroupsName.add(index, group);
        int id=group.getID();
        index= groupID_index(allGroupsID,0,allUsersID.size(),id);
        if(index==-1); //userID duplication (impossible)
        else allGroupsID.add(index, group);
    }
}












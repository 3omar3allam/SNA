package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class usefulFunctions {


    static int userNameBinarySearch(ArrayList<User> arr, int start, int end, String name){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        int compare = name.compareTo(arr.get(mid).getUserName());
        if(compare == 0) return mid;
        else if(compare < 0) return userNameBinarySearch(arr,start,mid,name);
        else return userNameBinarySearch(arr,mid+1,end,name);
    }
    static int name_index(ArrayList<User> arr, int start, int end, String username) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        int compare = username.compareTo(arr.get(mid).getUserName());
        if (compare == 0) return -1;
        else if (compare < 0) return name_index(arr, start, mid, username);
        else return name_index(arr, mid + 1, end, username);
    }

    static int userIDBinarySearch(ArrayList<User> arr, int start, int end, int ID){
        if(start >= end) return -1;
        int mid = (start + end - 1)/2;
        if(ID == arr.get(mid).getID()) return mid;
        else if(ID < arr.get(mid).getID()) return userIDBinarySearch(arr,start,mid,ID);
        else return userIDBinarySearch(arr,mid+1,end,ID);
    }

    static int ID_index(ArrayList<User> arr, int start, int end, int ID) {
        if (start >= end) {
            return start;
        }
        int mid = (start + end - 1) / 2;
        if (ID == arr.get(mid).getID()) return -1;
        else if (ID < arr.get(mid).getID()) return ID_index(arr, start, mid, ID);
        else return ID_index(arr, mid + 1, end, ID);
    }
}












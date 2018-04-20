package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class usefulFunctions {

    static int userIdBinarySearch(User arr[], int l, int r, int id)
            //returns the index if available, or -1 if unavailable
    {
        if (r>=l)
        {
            int mid = l + (r - l)/2;

            if (arr[mid].getID() == id)
                return mid;
            else if (arr[mid].getID() > id)
                return userIdBinarySearch(arr, l, mid-1, id);
            else return userIdBinarySearch(arr, mid+1, r, id);
        }
        else        return -1;
    }

    static int IndexAddingByIdSearch(int arr[], int l, int r, int id, boolean rightWay)
            //returns -1 if available, and the right index to add the item if available
            //rightWay ---> checks if I came from right or left side in previous iteration
    {
        if (r>=l)
        {
            int mid = l + (r - l)/2;
            if (arr[mid] == id)
                return -1;
            else if (arr[mid] > id)
                return IndexAddingByIdSearch(arr, l, mid-1, id, false);
            else return IndexAddingByIdSearch(arr, mid+1, r, id, true);
        }
        else
        {
            if(!rightWay)return l;
            else return r;
        }
    }

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
}












package sample;

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
            if(rightWay==false)return l;
            else return r;
        }
    }

    /*
    static int userNameBinarySearch(User arr[], int l, int r, String name){}

    static int IndexAddingByNameSearch(int arr[], int l, int r, String name, boolean rightWay){}

    boolean compareStrings(String s1, String S2){}
    */

}












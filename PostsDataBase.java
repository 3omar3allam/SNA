package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import static sample.User.allUsersID;
import static sample.User.allUsersName;

public class PostsDataBase {
    private static JSONObject Root = new JSONObject();
    private static JSONArray Users = new JSONArray();
    private static JSONArray PostsOfUser = new JSONArray();

    private static File file = new File("C:\\Users\\Ali Badawy\\Desktop\\Posts.json");

    public static void WritePost() {
        //add user name

        for (int i = 0; i < allUsersName.size(); i++) {
            LinkedList<Post> UserPosts = allUsersID.get(i).getPosts();
            for (int j = 0; j < UserPosts.size(); j++) {
                //declaring variables

                JSONObject postObject = new JSONObject();
                String PostContent = UserPosts.get(j).getContent();
                User owner = UserPosts.get(j).getOwner();
                String OwnerUserName=owner.getUserName();

                int NoOfLikes = UserPosts.get(j).getLikes();
                ArrayList<User> likers=UserPosts.get(j).getLikers();

                String PostContent = UserPosts.get(j).getContent();

                postObject.put("First Name", allUsersID.get(i).getFirstName());
                postObject.put("Last Name", allUsersID.get(i).getLastName());
                postObject.put("User Name", allUsersID.get(i).getUserName());
                postObject.put("ID", allUsersID.get(i).getID());
                postObject.put("Gender", allUsersID.get(i).getGender_string());
                postObject.put("Birth Date", allUsersID.get(i).getBirthDate().toString());
                postObject.put("Number of Friends", allUsersID.get(i).getNoFriends());
                postObject.put("List Of Friends", allUsersID.get(i).getFriends().toString());
                postObject.put("Number of Groups", allUsersID.get(i).getNoGroups());
                postObject.put("List of Groups", allUsersID.get(i).getGroups().toString());
                postObject.put("Number Of Posts", allUsersID.get(i).getNoPosts());

                PostsOfUser.add(postObject);
            }
            Users.add(PostsOfUser);
        }

    }
}
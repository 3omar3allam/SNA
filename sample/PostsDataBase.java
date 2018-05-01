package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import static sample.User.allUsersID;
import static sample.User.allUsersName;

public class PostsDataBase {
    private static JSONObject Root = new JSONObject();
    private static JSONArray Users = new JSONArray();
    private static JSONArray PostsOfUser = new JSONArray();

    private static File file = new File("C:\\Users\\Ali Badawy\\Desktop\\Posts.json");

    public static void WritePosts() {
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


                postObject.put("Post Date",UserPosts.get(j).getTime().toString());
                postObject.put("Post Content",PostContent);
                postObject.put("Post Owner",OwnerUserName);
                postObject.put("Number of Likes",NoOfLikes);
                postObject.put("Array of Likers",likers.toString());



                PostsOfUser.add(postObject);
            }
            Users.add(PostsOfUser);
        }
        writeInFile();
    }
    public static void writeInFile() {
        Root.put("Users", Users);

        // create the file
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(Root.toJSONString());
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        }


    }
}
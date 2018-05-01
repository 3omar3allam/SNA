package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static sample.User.allUsersID;
import static sample.User.allUsersName;
import static sample.usefulFunctions.userNameBinarySearch;

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
        Root.put("Posts", PostsOfUser);

        // create the file
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(Root.toJSONString());
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        }


    }
    public static void ReadPost(){
        try  {
            Scanner input = new Scanner(file);
            StringBuilder JSONin = new StringBuilder();
            while (input.hasNextLine()) {
                JSONin.append(input.nextLine());
            }
            //start parsing
            JSONParser parser = new JSONParser();
            JSONObject tempRoot = (JSONObject) parser.parse(JSONin.toString());
            //creating the posts array
            JSONArray tempPostArray = (JSONArray) tempRoot.get("Posts");
            for (int i=0;i<tempPostArray.size();i++){
                JSONObject tempPost= (JSONObject) tempPostArray.get(i);
               // System.out.println(tempPost.get("Post Owner"));
               String ownerUserName= (String) tempPost.get("Post Owner");
               int indexOfOwner=userNameBinarySearch(allUsersID,0,allUsersID.size(),ownerUserName);
                System.out.println((allUsersID.get(i).getUserName()));
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
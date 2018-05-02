package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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
                String OwnerUserName = owner.getUserName();

                int NoOfLikes = UserPosts.get(j).getLikes();
                ArrayList<User> likers = UserPosts.get(j).getLikers();


                postObject.put("Post Date", UserPosts.get(j).getTime().toString());
                postObject.put("Post Content", PostContent);
                postObject.put("Post Owner", OwnerUserName);
                postObject.put("Number of Likes", NoOfLikes);
                postObject.put("Array of Likers", likers);


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

    public static void ReadPost() {
        try {
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
          //  System.out.println(tempPostArray.toJSONString());
            //traverse on the array posts
            for (int i = 0; i < tempPostArray.size(); i++) {
                //get the temporary post
                JSONObject tempPost = (JSONObject) tempPostArray.get(i);
                //get post content
                String content = (String) tempPost.get("Post Content");
                //get the post owner userName
                String ownerUserName = (String) tempPost.get("Post Owner");
                int indexOfOwner = userNameBinarySearch(allUsersID, 0, allUsersID.size(), ownerUserName);
                String owner = (allUsersID.get(indexOfOwner)).getUserName();
                User U_owner = (allUsersID.get(indexOfOwner));
                //get number of likes
                long  NoOfLikes = (long) tempPost.get("Number of Likes");
                //create liker array
                ArrayList<User> likers = new ArrayList<User>();
                //get array of date of the post
                JSONArray tempLikersList = (JSONArray) tempPost.get("Array of Likers");
                //for loop for all likers
                for (int j = 0; j < tempLikersList.size(); j++) {
                    String tempFriendString = (String) tempLikersList.get(j);
                    //get the index of the liker
                    int indexOfLiker = userNameBinarySearch(allUsersID, 0, allUsersID.size(), tempFriendString);
                    //add to liker list
                    likers.add(allUsersID.get(indexOfLiker));
                }
                // get the time of posting

                String [] time=  tempPost.get("Post Date").toString().split("T");
                String Date1=time[0];
                String[]Date=Date1.split("-");
                int year = Integer.parseInt(Date[0]);
                int month = Integer.parseInt(Date[1]);
                int day = Integer.parseInt(Date[2]);
                String clock1=time[1];
                String[]clock=clock1.split(":");
                int h = Integer.parseInt(clock[0]);
                int m = Integer.parseInt(clock[1]);
                double s = Double.parseDouble(clock[2]);

                LocalDateTime timeAndDate ;
                timeAndDate= LocalDateTime.of(year,month,day,h,m, (int) s);
                Post Post = new Post(content);
                Post.setOwner(allUsersID.get(indexOfOwner));
                Post.setLikes((int)NoOfLikes);
                Post.setTime(timeAndDate);
                Post.setLikers(likers);
                U_owner.addPost(Post);


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
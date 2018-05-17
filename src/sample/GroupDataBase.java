package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static sample.Group.allGroupsID;
import static sample.Interface.WritePath;
import static sample.User.allUsersID;
import static sample.User.allUsersName;
import static sample.usefulFunctions.addToList;
import static sample.usefulFunctions.userNameBinarySearch;

public class GroupDataBase {
    private static JSONObject Root = new JSONObject();
    private static JSONArray Groups = new JSONArray();

    private static File file = new File(WritePath+"\\Group.json");

    public static void WriteGroup() {
        //add Group

        for (int i = 0; i < allGroupsID.size(); i++) {
            JSONObject groupObject = new JSONObject();

            groupObject.put("Group Name", allGroupsID.get(i).getName());
            groupObject.put("Group ID", allGroupsID.get(i).getID());
            groupObject.put("Group Admin", allGroupsID.get(i).getAdmin());
            groupObject.put("Group NoOfPosts", allGroupsID.get(i).getNoPosts());
            groupObject.put("Group NoOfMembers", allGroupsID.get(i).getNoMembers());
            groupObject.put("Group Members", allGroupsID.get(i).getMembers());
            //add to the group array
            Groups.add(groupObject);
        }
        writeGroupInFile();
    }

    public static void writeGroupInFile() {

        Root.put("Groups", Groups);

        file = new File(WritePath+"\\Group.json");
        // create the file
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(Root.toJSONString());
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        }
    }
     public static void ReadGroupFromFile(String path){
         File groupData = new File(path);

         try {
             Scanner input = new Scanner(groupData);
             StringBuilder JSONin = new StringBuilder();
             while (input.hasNextLine()) {
                 JSONin.append(input.nextLine());
             }
             //start parsing
             JSONParser parser = new JSONParser();

             JSONObject tempRoot = (JSONObject) parser.parse(JSONin.toString());

             //creating the Groups array
             JSONArray tempGroupArray = (JSONArray) tempRoot.get("Groups");
             for (int i = 0; i < tempGroupArray.size(); i++) {
                 JSONObject tempGroup = (JSONObject) tempGroupArray.get(i);
                 String Group_Name = tempGroup.get("Group Name").toString();
                 //get number of members
                 long  NoOfMembers = (long) tempGroup.get("Group NoOfMembers");
                 //create liker array
                 ArrayList<User> Members = new ArrayList<User>();
                 JSONArray tempMembersList = (JSONArray) tempGroup.get("Group Members");
                 String Admin_Name=tempGroup.get("Group Admin").toString();
                 //get the index of the Admin
                 int indexOfAdmin=userNameBinarySearch(allUsersName,0,allUsersName.size(),Admin_Name);
                 //create Admin
                User tempAdmin = allUsersID.get(indexOfAdmin);

                 //for loop for all Group Members
                 for (int j = 0; j < tempMembersList.size(); j++) {
                     String tempMemberString = (String) tempMembersList.get(j);
                     //get the index of the liker
                     int indexOfMember = userNameBinarySearch(allUsersName, 0, allUsersName.size(), tempMemberString);
                     //add to Member list
                     Members.add(allUsersName.get(indexOfMember));
                 }
                 Group tGroup = new Group(Group_Name,Members);
                 tGroup.setAdmin(tempAdmin);

                 try {
                     addToList(new Group(Group_Name,Members));
                 } catch (Exception ignored) {
                 }

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
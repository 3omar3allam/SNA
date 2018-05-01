package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

import static sample.User.allUsersID;
import static sample.User.allUsersName;
import static sample.usefulFunctions.addToList;


public class UsersDataBase {
    private static JSONObject Root = new JSONObject();
    private static JSONArray Users = new JSONArray();
    private static JSONArray readUsers = new JSONArray();

    private static File file = new File("C:\\Users\\Ali Badawy\\Desktop\\Users.json");


    public static void WriteUsers() {
        //add user name

        for (int i = 0; i < allUsersName.size(); i++) {
            JSONObject userObject = new JSONObject();
            userObject.put("First Name", allUsersID.get(i).getFirstName());
            userObject.put("Last Name", allUsersID.get(i).getLastName());
            userObject.put("User Name", allUsersID.get(i).getUserName());
            userObject.put("ID", allUsersID.get(i).getID());
            userObject.put("Gender", allUsersID.get(i).getGender_string());
            userObject.put("Birth Date", allUsersID.get(i).getBirthDate().toString());
            userObject.put("Number of Friends", allUsersID.get(i).getNoFriends());
            userObject.put("List Of Friends", allUsersID.get(i).getFriends().toString());
            userObject.put("Number of Groups", allUsersID.get(i).getNoGroups());
            userObject.put("List of Groups", allUsersID.get(i).getGroups().toString());
            userObject.put("Number Of Posts", allUsersID.get(i).getNoPosts());
         //   userObject.put("List of Posts", allUsersID.get(i).getPosts().toString());

            Users.add(userObject);


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

    public static void ReadFromFile() {

        System.out.println("reading");
        try {
            Scanner input = new Scanner(file);
            StringBuilder JSONin = new StringBuilder();
            while (input.hasNextLine()) {
                JSONin.append(input.nextLine());
            }
            // System.out.println(JSONin.toString());
            //start parsing
            JSONParser parser = new JSONParser();

            JSONObject tempRoot = (JSONObject) parser.parse(JSONin.toString());
            //creating the user array
            JSONArray tempUserArray = (JSONArray) tempRoot.get("Users");

            for (int i = 0; i < tempUserArray.size(); i++) {
                JSONObject tempUser= (JSONObject) tempUserArray.get(i);
                String [] dateArr=tempUser.get("Birth Date").toString().split("-");
                int year = Integer.parseInt(dateArr[0]);
                int month = Integer.parseInt(dateArr[1]);
                int day= Integer.parseInt(dateArr[2]);
                String First_Name=tempUser.get("First Name").toString();
                String User_Name=tempUser.get("User Name").toString();
                String Last_Name=tempUser.get("Last Name").toString();

                String Gender=tempUser.get("Gender").toString();

                try      {
                   addToList(new User(User_Name,First_Name,Last_Name,Gender,LocalDate.of(year,month,day)));
                }catch(Exception ignored){}
            }
            //for loop to go over all of the users
            for(int i = 0 ; i <allUsersID.size();i++){
                JSONObject tempUser =  (JSONObject) tempUserArray.get(i);
                //check to see if json member is the same as the member in allUsersID
                if ((allUsersID.get(i)).getUserName()==tempUser.get("User Name")){
                    //get the JSONArray of friends in current member
                    JSONArray UserFriends = (JSONArray) tempUser.get("List Of Friends");
                    for(int j = 0 ; j < UserFriends.size();i++){
                        //add the list of friends after finding the user that match the user name
                        for(int k = 0 ; k < allUsersID.size();k++){
                            JSONObject ju=(JSONObject) UserFriends.get(j);
                            User temp =allUsersID.get(k);
                        if(temp.getUserName()==ju.get("User Name")){
                            allUsersID.get(i).addFriend(temp);
                        }

                    }
                    }
                }
            }

         /**   for(int i = 0 ; i < allUsersID.size();i++){
                JSONObject tempUser =  (JSONObject) tempUserArray.get(i);
                if ((allUsersID.get(i)).getUserName()==tempUser.get("User Name")){
                    //get the JSONArray of Posts in current member
                    JSONArray UserPosts = (JSONArray) tempUser.get("List of Posts");
                    for(int j = 0 ; j < UserPosts.size();i++){
                       // (allUsersID.get(i)).addPost(/*string)
                    }
                     }

            }**/

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            System.out.println("error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
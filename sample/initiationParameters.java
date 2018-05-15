package sample;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;

import static sample.Group.allGroupsName;
import static sample.Interface.get_home_layout;
import static sample.User.*;
import static sample.usefulFunctions.*;
import static sample.usefulFunctions.*;

public class initiationParameters
{
    public static int display() {
        Stage window = new Stage();
        window.setTitle("initialize the program");
        GridPane mainPane=new GridPane();
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setVgap(7);
        mainPane.setHgap(3);
        Label l1=new Label("no of Users");
        Label l2=new Label("no of Groups");
        Label l3=new Label("no of Friends per User");
        Label l4=new Label("no of Members per Group");
        TextField noOfUsers = new TextField();
        TextField noOfGroups = new TextField();
        TextField minFriends= new TextField();
        minFriends.setPromptText("min");
        TextField maxFriends = new TextField();
        maxFriends.setPromptText("max");
        TextField minMembers = new TextField();
        minMembers.setPromptText("min");
        TextField maxMembers = new TextField();
        maxMembers.setPromptText("max");
        HBox Friends=new HBox(10);
        HBox Members=new HBox(10);
        Friends.getChildren().addAll(minFriends,maxFriends);
        Members.getChildren().addAll(minMembers,maxMembers);
        GridPane.setConstraints(l1,0,0);
        GridPane.setConstraints(l2,0,1);
        GridPane.setConstraints(l3,0,2);
        GridPane.setConstraints(l4,0,3);
        GridPane.setConstraints(noOfUsers,1,0);
        GridPane.setConstraints(noOfGroups,1,1);
        GridPane.setConstraints(Friends,1,2);
        GridPane.setConstraints(Members,1,3);
        mainPane.getChildren().addAll(l1,l2,l3,l4,noOfUsers,noOfGroups,Friends,Members);

        VBox v=new VBox(10);
        Button okay=new Button("OK");
        okay.setOnAction(e-> {
            try {
                initiate(noOfUsers,noOfGroups,minFriends,maxFriends,minMembers,maxMembers,window);
            } catch (Exception e1) {}
            get_home_layout();
        });

        v.getChildren().addAll(mainPane,okay);

        Scene scene=new Scene(v,500,300);
        window.setScene(scene);
        window.showAndWait();

        return 0;

    }

    private static void initiate(TextField noOfUsers, TextField noOfGroups, TextField minFriends, TextField maxFriends, TextField minMembers, TextField maxMembers, Stage window) throws Exception {
        int nousers=Integer.parseInt(noOfUsers.getText());
        int nogroups=Integer.parseInt(noOfGroups.getText());
        int minfriends=Integer.parseInt(minFriends.getText());
        int maxfriends=Integer.parseInt(maxFriends.getText());
        int minmembers=Integer.parseInt(minMembers.getText());
        int maxmembers=Integer.parseInt(maxMembers.getText());
        names_generator(nousers);
        initGroups(nogroups);
        friendship_initiator(minfriends,maxfriends);
        fillGroups(minmembers,maxmembers);
        window.close();

    }

    private static void names_generator(int no_of_users) {
        String line = null;
        ArrayList<String> male=new ArrayList<>(),female=new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("m.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) male.add(line);
            bufferedReader.close();
        }
        catch(Exception e){}
        try {
            FileReader fileReader = new FileReader("f.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                female.add(line);
            }
            bufferedReader.close();
        }
        catch (Exception e){}
        for(int i = 0; i<no_of_users;i++){
            try{
                Random rand = new Random();
                int index = rand.nextInt(female.size());
                int index2=rand.nextInt(male.size());
                String fn,sn, gender;
                if(i%2==0) {
                    fn = male.get(index);
                    gender="male";
                }
                else {fn = female.get(index); gender="female";}
                sn=male.get(index2);
                String userName=fn+sn;
                int Index = userName_index(allUsersName,0,allUsersName.size(),userName);
                while(Index==-1){userName+="1"; Index = userName_index(allUsersName,0,allUsersName.size(),userName);}
                addToList(new User(userName,fn,sn,gender,LocalDate.of(1996,7,7)));
            }catch(Exception ignored){}
        }
    }

    private static void friendship_initiator(int minNo,int maxNo) {
        for(int i=0;i<allUsersName.size();i++)
        {
            User current=allUsersName.get(i);
            Random rand = new Random();
            int index = minNo+rand.nextInt(maxNo-minNo);
            for(int j=0;j<index;j++)
            {
                Random r = new Random();
                int Index = rand.nextInt(allUsersName.size());
                User friend=allUsersName.get(Index);
                try {
                    current.addFriend(friend);
                } catch (Exception e) {}
            }
        }
    }
    private static void initGroups(int no_of_groups) throws Exception {
        Random rand = new Random();
        for(int i=0;i<no_of_groups;i++)
        {

            int index=rand.nextInt(allUsersName.size());
            Group g=new Group(("Group"+ Integer.toString(i)),allUsersName.get(index));
        }
    }
    private static void fillGroups(int minNo,int maxNo)
    {
        Random rand = new Random();
        for(int i=0;i<allGroupsName.size();i++)
        {
            int noOfUsers = minNo + rand.nextInt(maxNo-minNo);
            for(int j=0;j<noOfUsers;j++)
            {
                int index=rand.nextInt(allUsersName.size());
                allGroupsName.get(i).addMember(allUsersName.get(rand.nextInt(allUsersName.size())));
            }
        }
    }
}

package sample;

public class Thread implements Runnable {
    private int ID = -1;
    private String name = null;

    public Thread(String name){
        this.name = name;
    }

    public Thread(int ID){
        this.ID = ID;
    }


    public void run(){
        if(ID == -1){
            //call search by name function
        }
        else if(name == null){
            //call search by ID function
        }
    }
}
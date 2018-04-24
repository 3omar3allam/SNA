package sample;

public class CustomExceptions extends Exception {  //dummy class for file name only
    CustomExceptions(String message) {
        super(message);
    }
}

class UsernameException extends Exception{
    UsernameException(String message) {
        super(message);
    }
}
class GroupNameException extends Exception{
    GroupNameException(String message) {
        super(message);
    }
}
class NameException extends Exception{
    NameException(String message,Throwable cause){
        super(message,cause);
    }
}

class AgeException extends Exception{
    AgeException(String message) {
        super(message);
    }
}
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

class AgeException extends Exception{
    AgeException(String message) {
        super(message);
    }
}
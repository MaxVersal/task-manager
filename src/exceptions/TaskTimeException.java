package exceptions;

import java.io.IOException;

public class TaskTimeException extends IOException{

    public TaskTimeException(String message){
        super(message);
    }
}

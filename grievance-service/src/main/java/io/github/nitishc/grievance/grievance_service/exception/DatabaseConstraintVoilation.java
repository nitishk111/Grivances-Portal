package io.github.nitishc.grievance.grievance_service.exception;

public class DatabaseConstraintVoilation extends Exception{

    public DatabaseConstraintVoilation(String msg){
        super(msg);
    }
}

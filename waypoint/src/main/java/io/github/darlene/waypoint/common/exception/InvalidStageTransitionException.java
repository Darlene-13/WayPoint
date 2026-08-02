package io.github.darlene.waypoint.common.exception;


public class InvalidStageTransitionException extends RuntimeException{

    public InvalidStageTransitionException(String message){
        super(message);
    }
}
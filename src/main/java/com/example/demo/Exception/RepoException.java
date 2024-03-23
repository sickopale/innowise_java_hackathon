package com.example.demo.Exception;

public class RepoException extends Exception{
    public RepoException(){

    }
    public RepoException(String message){
        super(message);
    }
    public RepoException(String massage, Throwable cause){
        super(massage,cause);
    }
    public RepoException(Throwable cause){
        super(cause);
    }
}
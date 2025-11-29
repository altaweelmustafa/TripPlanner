package com.example.assone;
import java.io.Serializable;

public class TodoItem implements Serializable {
    public String text;
    public boolean done;

    public TodoItem(String text, boolean done) {
        this.text = text;
        this.done = done;
    }
}

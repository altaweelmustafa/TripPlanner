package com.example.assone;
import java.io.Serializable;
import java.util.ArrayList;

public class Trip implements Serializable {
    public String name;
    public String location;
    public double totalPrice;
    public ArrayList<TodoItem> todos;
    public String date;

    public Trip(String name, String location, double totalPrice, String date) {
        this.name = name;
        this.location = location;
        this.totalPrice = totalPrice;
        this.date = date;
    }
}

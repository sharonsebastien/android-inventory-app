package com.example.inventoryapp;

public class ModelCategory {
    int _id;
    String _name;

    public ModelCategory() {}

    public ModelCategory(int id,String name) {
        this._id = id;
        this._name = name;
    }

    public ModelCategory(String name) {
        this._name = name;
        }

    public int getId() {return _id;}
    public void setId(int id) {this._id = id;}

    public String getName() {return this._name; }
    public void setName(String name) {this._name = name;}
}

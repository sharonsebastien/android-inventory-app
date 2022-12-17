package com.example.inventoryapp;

public class ModelUser {
    int _id;
    String _name;
    String _phoneNumber;
    String _password;
    String _userType;

    public ModelUser() {}

    public ModelUser(int id,String name,String phoneNumber, String password,String userType) {
        this._id = id;
        this._name = name;
        this._phoneNumber = phoneNumber;
        this._password = password;
        this._userType = userType;
    }

    public ModelUser(String name,String phoneNumber, String password,String userType) {
        this._phoneNumber = phoneNumber;
        this._password = password;
        this._name = name;
        this._userType = userType;
    }

    public int getId() {return _id;}
    public void setId(int id) {this._id = id;}

    public String getName() {return this._name; }
    public void setName(String name) {this._name = name;}

    public String getUserType() {return this._userType; }
    public void setUserType(String userType) {this._userType = userType;}

    public String getPhoneNumber() {return this._phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {this._phoneNumber = phoneNumber;}

    public String getPassword() {return this._password; }
    public void setPassword(String password) {this._password = password;}
}

package com.example.ariamobile;

/**
 * Created by Mariano on 22/09/13.
 */


public class Reservation {

    //private variables
    int _id;
    String _name;
    String _last_name;
    String _email;
    String _phone;
    String _in;
    String _out;
    int _nights;
    String _reservation_number;


    // Empty constructor
    public Reservation(){

    }
    // constructor
    public Reservation(int id, String name, String last_name, String email,  String phone, String in, String out, String nights, String reservation_number){
        this._id = id;
        this._name = name;
        this._last_name = last_name;
        this._email = email;
        this._phone = phone;
        this._in = in;
        this._out = out;
        this._nights = Integer.parseInt(nights);
        this._reservation_number = reservation_number;
    }

    // constructor
    public Reservation(String name, String last_name, String email,  String phone, String in, String out, String nights, String reservation_number){
        this._name = name;
        this._last_name = last_name;
        this._email = email;
        this._phone = phone;
        this._in = in;
        this._out = out;
        this._nights = Integer.parseInt(nights);
        this._reservation_number = reservation_number;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting last name
    public String getLastName(){
        return this._last_name;
    }

    // setting last name
    public void setLastName(String last_name){
        this._last_name = last_name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone = phone_number;
    }

    // getting email
    public String getEmail(){
        return this._email;
    }

    // setting email
    public void setEmail(String email){
        this._email = email;
    }

    // getting in
    public String getIn(){
        return this._in;
    }

    // setting in
    public void setIn(String in){
        this._in = in;
    }

    // getting out
    public String getOut(){
        return this._out;
    }

    // setting out
    public void setOut(String out){
        this._out = out;
    }

    // getting nights
    public int getNights(){
        return this._nights;
    }

    // setting nights
    public void setNights(int nights){
        this._nights = nights;
    }

    // getting reservation number
    public String getReservationNumber(){
        return this._reservation_number;
    }

    // setting reservation number
    public void setReservationNumber(String reservation_number){
        this._reservation_number = reservation_number;
    }
}

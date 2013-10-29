package com.example.ariamobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mariano on 22/09/13.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "reservation";

    // Contacts table name
    private static final String TABLE_RESERVATION = "reservation";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LS_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PH_NO = "phone";
    private static final String KEY_IN = "in";
    private static final String KEY_OUT = "out";
    private static final String KEY_NIGHTS = "nights";
    private static final String KEY_RSV_NUMBER = "reservation_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RESERVATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"
                + KEY_LS_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_IN + " TEXT,"
                + KEY_OUT + " TEXT,"
                + KEY_NIGHTS + " TEXT,"
                + KEY_RSV_NUMBER + " TEXT," + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);

        // Create tables again
        onCreate(db);
    }

    // Adding new reserva
    public void addReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, reservation.getName()); // Contact Name
        values.put(KEY_LS_NAME, reservation.getLastName()); // Contact Name
        values.put(KEY_EMAIL, reservation.getEmail()); // Contact Name
        values.put(KEY_PH_NO, reservation.getPhoneNumber()); // Contact Phone Number
        values.put(KEY_IN, reservation.getIn()); // Contact Name
        values.put(KEY_OUT, reservation.getOut()); // Contact Name
        values.put(KEY_NIGHTS, reservation.getNights()); // Contact Name
        values.put(KEY_RSV_NUMBER, reservation.getReservationNumber()); // Contact Name

        // Inserting Row
        db.insert(TABLE_RESERVATION, null, values);
        db.close(); // Closing database connection
    }

    // Getting single reserva
    public Reservation getReservation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESERVATION, new String[] { KEY_ID, KEY_NAME, KEY_LS_NAME, KEY_EMAIL,KEY_PH_NO, KEY_IN, KEY_OUT, KEY_NIGHTS, KEY_RSV_NUMBER }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Reservation reservation = new Reservation(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        // return contact
        return reservation;
    }

    /* Getting All reserva
    public List<Reservation> getAllReservations() {
    List<Contact> contactList = new ArrayList<Contact>();
    //Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Contact contact = new Contact();
            contact.setID(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            // Adding contact to list
            contactList.add(contact);
        } while (cursor.moveToNext());
    }

    // return contact list
    return contactList;
     }*/

    // Getting reserva Count
    public int getReservationsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RESERVATION;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            return cursor.getCount();
        } catch (Exception e) {
            return 5;
        }

    }


    // Updating single reserva
    public int updateReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, reservation.getName());
        values.put(KEY_LS_NAME, reservation.getLastName());
        values.put(KEY_EMAIL, reservation.getEmail());
        values.put(KEY_PH_NO, reservation.getPhoneNumber());
        values.put(KEY_IN, reservation.getIn());
        values.put(KEY_OUT, reservation.getOut());
        values.put(KEY_NIGHTS, reservation.getNights());
        values.put(KEY_RSV_NUMBER, reservation.getReservationNumber());

        // updating row
        return db.update(TABLE_RESERVATION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(reservation.getID()) });
    }

    // Deleting single reserva
    public void deleteReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESERVATION, KEY_ID + " = ?",
                new String[] { String.valueOf(reservation.getID()) });
        db.close();
    }
}
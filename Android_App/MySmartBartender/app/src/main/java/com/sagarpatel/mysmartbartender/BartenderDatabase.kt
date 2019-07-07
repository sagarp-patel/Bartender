package com.sagarpatel.mysmartbartender

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BartenderDatabase(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_DRINKS_TABLE = ("CREATE TABLE "+ TABLE_NAME+"("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + DRINK_NAME + " TEXT,"
                + INGREDIENT_1 + " Int,"
                + INGREDIENT_2 + " Int,"
                + INGREDIENT_3 + " Int,"
                + INGREDIENT_4 + " Int,"
                + INGREDIENT_5 + " Int,"
                + INGREDIENT_6 + " Int"
                + ")"
                )
        p0!!.execSQL(CREATE_DRINKS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(p0)
    }

    fun addDrink(drink:Drink){ // Adding a new Drink to the Database
        val db = this.writableDatabase // Get the database
        val values = ContentValues() // You have to use this in order to push values into the table
        values.put(BartenderDatabase.DRINK_NAME,drink.getName())
        values.put(BartenderDatabase.INGREDIENT_1,drink.pump_1)
        values.put(BartenderDatabase.INGREDIENT_2,drink.pump_2)
        values.put(BartenderDatabase.INGREDIENT_3,drink.pump_3)
        values.put(BartenderDatabase.INGREDIENT_4,drink.pump_4)
        values.put(BartenderDatabase.INGREDIENT_5,drink.pump_5)
        values.put(BartenderDatabase.INGREDIENT_6,drink.pump_6)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

     fun getAllDrinks(): Cursor?{
         val db = this.readableDatabase
         return db.rawQuery("SELECT * FROM $TABLE_NAME",null)
     }

    companion object{
        private const val DATABASE_NAME = "Bartender.db"
        private const val DATABASE_VERSION = 1
        val TABLE_NAME = "Drink_Recipe"
        val COLUMN_ID = "_id"
        val DRINK_NAME = "drink_name"
        val INGREDIENT_1 = "Ingredient_1"
        val INGREDIENT_2 = "Ingredient_2"
        val INGREDIENT_3 = "Ingredient_3"
        val INGREDIENT_4 = "Ingredient_4"
        val INGREDIENT_5 = "Ingredient_5"
        val INGREDIENT_6 = "Ingredient_6"
    }

}
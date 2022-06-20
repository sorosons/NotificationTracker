package com.example.notification_ap

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sgmy.notificationtrackerkt.model.AppListDataModel
import com.sgmy.notificationtrackerkt.model.NotiDataModel

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val notificationTable = ("CREATE TABLE " + NOTI_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PACK_COl + " TEXT," +
                TITLE_COL + " TEXT," +
                TEXT_COl + " TEXT," +
                BIGICON_COL + " BLOB" +
                ")")

        val appTable = ("CREATE TABLE " + APP_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                APPS_PACKAGE_NAME_COL + " TEXT," +
                APPS_NAME_COL + " TEXT" +

                ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(notificationTable)
        db.execSQL(appTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + NOTI_TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addNotificaiton(notiData: NotiDataModel){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(PACK_COl,notiData.packageName)
        values.put(TITLE_COL, notiData.tittle)
        values.put(TEXT_COl, notiData.text)
       // values.put(BIGICON_COL, notiData.bigIcon)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(NOTI_TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }


    // This method is for adding data in our database
    fun addAppsName(appsModel: AppListDataModel){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(APPS_PACKAGE_NAME_COL,appsModel.packageName)
        values.put(APPS_NAME_COL, appsModel.myAppName)



        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(APP_TABLE, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    @SuppressLint("Range")
    fun getName(): ArrayList<NotiDataModel>? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        var notList:ArrayList<NotiDataModel>?=ArrayList<NotiDataModel>()


        // below code returns a cursor to
        // read data from the database
        val cursor= db.rawQuery("SELECT * FROM " + NOTI_TABLE_NAME, null)
        cursor!!.moveToFirst()
        while(cursor.moveToNext()){
            var packname=cursor.getString(cursor.getColumnIndex(DBHelper.PACK_COl))
            var title=cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL))
            var text=cursor.getString(cursor.getColumnIndex(DBHelper.TEXT_COl))
            var icon=cursor.getBlob(cursor.getColumnIndex(DBHelper.BIGICON_COL))
            notList?.add(NotiDataModel(packname,title,text,null))

        }

        return  notList
    }


    fun getOneName(packagename: String): Boolean {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $APP_TABLE WHERE $APPS_PACKAGE_NAME_COL = ?"
        db.rawQuery(selectQuery, arrayOf(packagename)).use { // .use requires API 16
            if (it.moveToFirst()) {

                return true
            }
        }
        return false
    }
    fun isExist(packagename: String): Boolean {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $APP_TABLE WHERE $APPS_PACKAGE_NAME_COL = ?"
        db.rawQuery(selectQuery, arrayOf(packagename)).use {
          if (it.moveToFirst()) {

              return true
          }
      }

        return false


    }
    fun deleteCourse(packgagename: String?) {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(APP_TABLE, "packagename=?", arrayOf(packgagename))
        db.close()
    }



    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "NOTIDB"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val NOTI_TABLE_NAME = "noti_table"
        val ID_COL = "id"
        val PACK_COl = "packgagename"
        val TITLE_COL = "tittle"
        val TEXT_COl = "text"
        val BIGICON_COL = "bigiocn"



        val APP_TABLE = "app_table"
        val APPS_PACKAGE_NAME_COL = "packagename"
        val APPS_NAME_COL = "appname"
    }
}


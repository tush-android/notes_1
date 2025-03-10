package com.example.bsc_p6

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.bsc_p6.Dbitem.Companion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DbHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME="teadb1"//tea12.db
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME1="alluser"
        private const val COLUMN_ID="id"
        private const val COLUMN_NAME="name"
        private const val COLUMN_MOBILE="mobile"
        private const val COLOMN_ADDR="address"
        private const val TABLE_NAME2="allitemdetails"
        private const val ITEM_ID="id"
        private const val ITEM_NAME="itemname"
        private const val ITEM_PRICE="itemprice"
        private const val TABLE_NAME3="teaorder"
        private const val OID="id"
        private const val OR_BY="order_by"
        private const val I_Na="item_name"
        private const val Q_it="item_quantity"
        private const val I_pi="item_price"
        private const val total="total"
        private const val total_price="total_price"
        private const val dateone="dateone"
        private const val yesorno="Y"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       // val creaateTableQuery="CREATE TABLE $TABLE_NAME1($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_NAME TEXT,$COLUMN_MOBILE TEXT,$COLOMN_ADDR TEXT)"
       // db?.execSQL(creaateTableQuery)
        val createUserTableQuery =
            """
            CREATE TABLE $TABLE_NAME1 (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_MOBILE TEXT,
                $COLOMN_ADDR TEXT
            )
        """
        db?.execSQL(createUserTableQuery)
       val createItemTableQuery="""
            CREATE TABLE $TABLE_NAME2 (
                $ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $ITEM_NAME TEXT,
                $ITEM_PRICE TEXT
            )
        """
        db?.execSQL(createItemTableQuery)
        val createorderTab= """
            CREATE TABLE $TABLE_NAME3($OID INTEGER PRIMARY KEY AUTOINCREMENT,$OR_BY TEXT,$I_Na TEXT,$I_pi REAL,$Q_it REAL,$total REAL,$total_price REAL,$dateone DEFAULT CURRENT_TIMESTAMP,$yesorno TEXT DEFAULT 'Y'
        """.trimIndent()
        db?.execSQL(createorderTab)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropUserTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME1"
       val dropItemTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME2"
        val droporder="DROP TABLE IF EXISTS $TABLE_NAME3"

        db?.execSQL(dropUserTableQuery)
        db?.execSQL(dropItemTableQuery)
        db?.execSQL(droporder)
        onCreate(db)
    }
    fun insertuser(user:customer){
        val db=writableDatabase
        val values= ContentValues().apply {
            put(COLUMN_NAME,user.name)
            put(COLUMN_MOBILE,user.mob)
            put(COLOMN_ADDR,user.addr)
        }
        db.insert(TABLE_NAME1,null,values)
        db.close()
    }
    fun getAllUser():List<customer>
    {
        val custlist= mutableListOf<customer>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME1"
        val cursor=db.rawQuery(query,null)
        while(cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val mob=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOBILE))
            val addr=cursor.getString(cursor.getColumnIndexOrThrow(COLOMN_ADDR))

            val cus=customer(id,name,mob,addr)
            custlist.add(cus)
        }
        cursor.close()
        db.close()
        return custlist
    }
    fun update(cus:customer){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_NAME,cus.name)
            put(COLUMN_MOBILE,cus.mob)
            put(COLOMN_ADDR,cus.addr)
        }
        val whereclause="$COLUMN_ID=?"
        val whereargs= arrayOf(cus.id.toString())
        db.update(TABLE_NAME1,values,whereclause,whereargs)
        db.close()
    }
    fun getuserbyId(userid:Int):customer{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME1 WHERE $COLUMN_ID=$userid"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()
        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val cname=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val cmob=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOBILE))
        val caddr=cursor.getString(cursor.getColumnIndexOrThrow(COLOMN_ADDR))
        cursor.close()
        db.close()
        return customer(id,cname,cmob,caddr)
    }
    fun deleteuser(userid:Int){
        val db=writableDatabase
        val whereClause="$COLUMN_ID = ?"
        val whereargs= arrayOf(userid.toString())
        db.delete(TABLE_NAME1,whereClause,whereargs)
        db.close()
    }
    fun getallusername():List<String>{
        val userList = mutableListOf<String>()
        userList.add("---Select User---")
        val db=readableDatabase
        val query="SELECT $COLUMN_NAME FROM $TABLE_NAME1"
        val cursor=db.rawQuery(query,null)
        while(cursor.moveToNext()){
            val user=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            userList.add(user)
        }
        cursor.close()
        db.close()
        return userList
    }
  fun additem(item:Items){
        val db=writableDatabase
        val values= ContentValues().apply {
            put(ITEM_NAME,item.ina)
            put(ITEM_PRICE,item.itp)
        }
        db.insert(TABLE_NAME2,null,values)
        db.close()
    }
    fun getallitems():List<Items>{
        val itlist= mutableListOf<Items>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME2"
        val cursor=db.rawQuery(query,null)
        while(cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(Dbitem.ITEM_ID))
            val n=cursor.getString(cursor.getColumnIndexOrThrow(Dbitem.ITEM_NAME))
            val p=cursor.getString(cursor.getColumnIndexOrThrow(Dbitem.ITEM_PRICE))

            val i=Items(id,n,p)
            itlist.add(i)
        }
        cursor.close()
        db.close()
        return itlist
    }
    val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
        Date()
    )
    val status="Y"
    //data class MenuItem(val id: Int, val name: String, val price: String)
    fun addOrder(ord:orders){
        val db=writableDatabase
        val values= ContentValues().apply {
            put(OID,ord.id)
            put(OR_BY,ord.oby)
            put(I_Na,ord.oitname)
            put(I_pi,ord.oip)
            put(Q_it,ord.qun)
            put(total,ord.Total)
            put(dateone,ord.date)
            put(status,ord.status)
            put(total_price,ord.finaltotal)
        }
        db.insert(TABLE_NAME3,null,values)
        db.close()
    }
}
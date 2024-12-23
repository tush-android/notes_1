package com.example.bsc_p6

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bsc_p6.databinding.ActivityNeworderdashBinding
import java.util.Calendar


class neworderdash : AppCompatActivity() {
    private lateinit var binding:ActivityNeworderdashBinding
    private lateinit var textViewDate: TextView
    private lateinit var db:DbHelper
    private lateinit var Orderadapter:orderAdapter
    private lateinit var recycle:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNeworderdashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textViewDate = findViewById(R.id.textViewDate)
        val calendar = Calendar.getInstance()
        val todayYear = calendar.get(Calendar.YEAR)
        val todayMonth = calendar.get(Calendar.MONTH) + 1 // Months are 0-indexed
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

        textViewDate.text = getString(R.string.date_display_format, todayDay, todayMonth, todayYear)
        db=DbHelper(this@neworderdash)
        //val menuItems=db.getallitems()
        /*val tl=binding.tablemenu
        for(i in menuItems){
            val tableRow=TableRow(this@neworderdash)
            val itemNameTextView=TextView(this@neworderdash)
            itemNameTextView.text=i.ina
            tableRow.addView(itemNameTextView)
            tl.addView(tableRow)
        }*/
       /* val tableLayout = findViewById<TableLayout>(R.id.tablemenu)
        for (menuItem in menuItems) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            // Item Name
            val itemNameTextView = TextView(this)
            itemNameTextView.text = menuItem.ina
            itemNameTextView.setPadding(8, 8, 8, 8)
            tableRow.addView(itemNameTextView)

            // Quantity Controls (LinearLayout with -, EditText, +)
            val quantityLayout = LinearLayout(this)
            quantityLayout.orientation = LinearLayout.HORIZONTAL
            quantityLayout.setPadding(8, 8, 8, 8)

            // Price TextView
            val priceTextView = TextView(this)
            priceTextView.text = "₹0"
            priceTextView.setPadding(8, 8, 8, 8)
            tableRow.addView(priceTextView)

            // Add the row to the table
            tableLayout.addView(tableRow)
        }*/
        val itemList = db.getallitems()
        recycle=findViewById(R.id.recycler_view)
        Orderadapter=orderAdapter(this@neworderdash,itemList)
        recycle.adapter=Orderadapter
        recycle.layoutManager=LinearLayoutManager(this@neworderdash)
    }
}
package com.example.bsc_p6

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bsc_p6.databinding.ActivityNewOrderUseroneBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class newOrderUserone : AppCompatActivity() {
    private lateinit var binding:ActivityNewOrderUseroneBinding
    private lateinit var db:DbHelper
    var grandTotal = 0.0
    private fun calculateTotal(quantity: Int, price: String): String {
        val priceValue = price.toDoubleOrNull() ?: 0.0
        return (quantity * priceValue).toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewOrderUseroneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=DbHelper(this@newOrderUserone)
        val items=db.getallitems()
        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout)
        val scrollView = findViewById<ScrollView>(R.id.scroll)
        val totalPriceTextView = findViewById<TextView>(R.id.totalPriceTextView)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val calendar = Calendar.getInstance()
        val todayYear = calendar.get(Calendar.YEAR)
        val todayMonth = calendar.get(Calendar.MONTH) + 1 // Months are 0-indexed
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

        textViewDate.text = getString(R.string.date_display_format, todayDay, todayMonth, todayYear)

        val userList=db.getallusername()
        val adapter= ArrayAdapter(this@newOrderUserone,android.R.layout.simple_spinner_item,userList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.cusspinner.adapter=adapter

        val scrollContainer = scrollView.getChildAt(0) as LinearLayout

        //  val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
       /* for(i in items){
            val tableRow=TableRow(this@newOrderUserone)
            val itemNameTextView = TextView(this)
            itemNameTextView.text = i.ina
            itemNameTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
            itemNameTextView.textSize = 18f



            val itemPriceTextView = TextView(this)
            itemPriceTextView.text = i.itp
            itemPriceTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            itemPriceTextView.textSize = 18f
            val quantityTextView = TextView(this)
            quantityTextView.layoutParams = TableRow.LayoutParams(2, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
            quantityTextView.textSize = 18f

           /* val quantityEditText = EditText(this)
            quantityEditText.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
            quantityEditText.textSize = 18f
            quantityEditText.inputType = InputType.TYPE_CLASS_NUMBER
            quantityEditText.width = 10 // Set the width of the EditText*/
            val quantityEditText = EditText(this)
            val layoutParams = TableRow.LayoutParams(24, TableRow.LayoutParams.WRAP_CONTENT, 0.5f)
           // layoutParams.width =
            quantityEditText.layoutParams = layoutParams

            quantityEditText.textSize = 18f
            quantityEditText.inputType = InputType.TYPE_CLASS_NUMBER

            val totalPriceTextView = TextView(this)
            totalPriceTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            totalPriceTextView.textSize = 18f
            totalPriceTextView.gravity = Gravity.END

            tableRow.addView(itemNameTextView)
            //tableRow.addView(quantityTextView)
            tableRow.addView(quantityEditText)
            tableRow.addView(itemPriceTextView)
            tableRow.addView(totalPriceTextView)

            tableLayout.addView(tableRow)
        }
        fun dpToPx(dp: Int, resources: Resources): Int {
            return (dp * resources.displayMetrics.density).toInt()
        }*/
       /* for (item in items) {
            // Create a new LinearLayout for each item
            val itemLayout = LinearLayout(this)  // or getContext() if you're in a fragment
            itemLayout.orientation = LinearLayout.HORIZONTAL
            itemLayout.setPadding(8, 8, 8, 8)  // Add padding around the row
            itemLayout.weightSum = 4f

            // Create and configure Item Name TextView
            val itemNameTextView = TextView(this)
            itemNameTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemNameTextView.text = item.ina
            itemNameTextView.gravity = Gravity.START
            itemNameTextView.textSize = 16f

            // Create and configure Quantity EditText
            val itemQuantityEditText = EditText(this)
            itemQuantityEditText.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemQuantityEditText.inputType = InputType.TYPE_CLASS_NUMBER
            itemQuantityEditText.hint = "Qty"
            itemQuantityEditText.textSize = 14f

            // Create and configure Item Price TextView
            val itemPriceTextView = TextView(this)
            itemPriceTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemPriceTextView.text = item.itp
            itemPriceTextView.gravity = Gravity.END
            itemPriceTextView.textSize = 16f
            itemPriceTextView.setPadding(8, 0, 0, 0)

            // Create and configure Item Total Price TextView
            val itemTotalPriceTextView = TextView(this)
            itemTotalPriceTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemTotalPriceTextView.text = calculateTotal(item.itp)
            itemTotalPriceTextView.gravity = Gravity.END
            itemTotalPriceTextView.textSize = 16f
            itemTotalPriceTextView.setPadding(8, 0, 0, 0)

            // Add all the views to the itemLayout (which is a horizontal LinearLayout)
            itemLayout.addView(itemNameTextView)
            itemLayout.addView(itemQuantityEditText)
            itemLayout.addView(itemPriceTextView)
            itemLayout.addView(itemTotalPriceTextView)

            // Finally, add the itemLayout (the row) to the parent LinearLayout
            linearLayout.addView(itemLayout)
        }*/
        for (item in items) {
            // Create a new LinearLayout for each item
            val itemLayout = LinearLayout(this)  // or getContext() if you're in a fragment
            itemLayout.orientation = LinearLayout.HORIZONTAL
            itemLayout.setPadding(8, 8, 8, 8)  // Add padding around the row
            itemLayout.weightSum = 4f

            // Create and configure Item Name TextView
            /*val itemNameTextView = TextView(this)
            itemNameTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemNameTextView.text = item.ina
            itemNameTextView.gravity = Gravity.START
            itemNameTextView.textSize = 14f*/
            val itemNameTextView = TextView(this)
            itemNameTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemNameTextView.text = item.ina
            itemNameTextView.gravity = Gravity.START
            itemNameTextView.textSize = 16f
            itemNameTextView.setTypeface(itemNameTextView.typeface,android.graphics.Typeface.BOLD)
            itemNameTextView.setTextColor(ContextCompat.getColor(this,R.color.price))
            itemNameTextView.setPadding(8, 4, 8, 12)

            // Create and configure Quantity EditText
            val itemQuantityEditText = EditText(this)
            itemQuantityEditText.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            itemQuantityEditText.inputType = InputType.TYPE_CLASS_NUMBER
            itemQuantityEditText.hint = "Qty"
            itemQuantityEditText.gravity = Gravity.CENTER
            itemQuantityEditText.textSize = 16f
           // itemQuantityEditText.setBackgroundColor(ContextCompat.getColor(this,R.color.lavender))
            itemQuantityEditText.setBackgroundResource(R.drawable.border)
            itemQuantityEditText.setPadding(8, 4, 8, 12)

            // Create and configure Item Price TextView
            val itemPriceTextView = TextView(this)
            itemPriceTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemPriceTextView.text = item.itp
            itemPriceTextView.gravity = Gravity.END
            itemPriceTextView.textSize = 16f
            itemPriceTextView.setTextColor(ContextCompat.getColor(this,R.color.to))
            itemPriceTextView.setPadding(8, 4, 8, 12)

            // Create and configure Item Total Price TextView
            val itemTotalPriceTextView = TextView(this)
            itemTotalPriceTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            itemTotalPriceTextView.text = calculateTotal(0,item.itp)  // Default quantity to 1 for now
            itemTotalPriceTextView.gravity = Gravity.RIGHT
            itemTotalPriceTextView.textSize = 16f
            itemTotalPriceTextView.setTextColor(ContextCompat.getColor(this,R.color.red))
            itemTotalPriceTextView.setPadding(8, 0, 0, 0)

            // Add all the views to the itemLayout (which is a horizontal LinearLayout)
            itemLayout.addView(itemNameTextView)
            itemLayout.addView(itemQuantityEditText)
            itemLayout.addView(itemPriceTextView)
            itemLayout.addView(itemTotalPriceTextView)

            // Finally, add the itemLayout (the row) directly into the ScrollView
            // Note: ScrollView can only have one child, so we need to add views to its direct child (a LinearLayout)
            val scrollContainer = scrollView.getChildAt(0) as LinearLayout
            scrollContainer.addView(itemLayout)

            itemQuantityEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // When the user changes the quantity, update the total price
                    val quantityText = s.toString()
                    val quantity = quantityText.toIntOrNull() ?: 0  // Default to 1 if input is invalid
                    val totalPrice = calculateTotal(quantity, item.itp)
                    itemTotalPriceTextView.text = totalPrice

                    grandTotal = 0.0
                    for (i in 0 until scrollContainer.childCount) {
                        val childLayout = scrollContainer.getChildAt(i) as LinearLayout
                        val totalTextView = childLayout.getChildAt(3) as TextView
                        val itemTotal = totalTextView.text.toString().toDoubleOrNull() ?: 0.0
                        grandTotal += itemTotal
                    }
                    totalPriceTextView.text = String.format("%.2f", grandTotal)

                // Update the total price TextView

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No action needed here
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // No action needed here
                }
            })
            val order=binding.placeOrderButton.setOnClickListener{
                val currentDateTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(
                    Date()
                )
                val selectedCustomerName = binding.cusspinner.selectedItem.toString()
                val orderDate = currentDateTime
                var finalTotal = 0.0
                for(item in items){
                    val quantityText=(scrollContainer.getChildAt(items.indexOf(item)) as LinearLayout).getChildAt(1) as EditText
                    val quantityone=quantityText.text.toString().toIntOrNull() ?: 0
                    val total=calculateTotal(quantityone,item.itp).toDouble()
                    finalTotal += total
                }
                val order=orders(
                    id= 0,
                    oby=selectedCustomerName,
                    oitname = items[0].ina,
                    qun = 1, // You might need to loop through quantities
                    oip = items[0].itp, // Item price
                    Total = finalTotal.toInt(),
                    finaltotal = finalTotal.toInt(),
                    date = orderDate,
                    status = "Y"
                )
            }
        }





        // Function to update the total price

        }
    }
/*fun dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}*/

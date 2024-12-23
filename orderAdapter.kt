package com.example.bsc_p6

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class orderAdapter(private val context:Context,private val itemList:List<Items>):RecyclerView.Adapter<orderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderAdapter.ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.order_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: orderAdapter.ViewHolder, position: Int) {
       val item=itemList[position]
        holder.itemNameTextView.text = item.ina
        holder.itemPriceTextView.text = item.itp
        holder.quantityEditText.setText("0")



      /*  holder.decreaseButton.setOnClickListener {
            var quantity = holder.quantityEditText.text.toString().toInt()
            if (quantity > 0) {
                quantity--
                holder.quantityEditText.setText(quantity.toString())
                val totalPrice = quantity * item.itp.toInt()
                holder.totalPriceTextView.text = totalPrice.toString()
            }
        }

        holder.increaseButton.setOnClickListener {
            var quantity = holder.quantityEditText.text.toString().toInt()
            quantity++
            holder.quantityEditText.setText(quantity.toString())
            val totalPrice = quantity * item.itp.toInt()
            holder.totalPriceTextView.text = totalPrice.toString()
        }*/
        holder.quantityEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.toString() != "") {
                    val quantity = s.toString().toInt()
                    val totalPrice = quantity * item.itp.toInt()
                    holder.totalPriceTextView.text = totalPrice.toString()
                } else {
                    holder.totalPriceTextView.text = "0"
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        holder.decreaseButton.setOnClickListener {
            var quantity = if (holder.quantityEditText.text.toString() == "") 0 else holder.quantityEditText.text.toString().toInt()
            if (quantity > 0) {
                quantity--
                holder.quantityEditText.setText(quantity.toString())
            }
        }

        holder.increaseButton.setOnClickListener {
            var quantity = if (holder.quantityEditText.text.toString() == "") 0 else holder.quantityEditText.text.toString().toInt()
            quantity++
            holder.quantityEditText.setText(quantity.toString())
        }
    }

    override fun getItemCount(): Int=itemList.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNameTextView: TextView = view.findViewById(R.id.item_name)
        val itemPriceTextView: TextView = view.findViewById(R.id.item_price)
        val quantityEditText: EditText = view.findViewById(R.id.quantity_edit_text)
        val decreaseButton: ImageButton = view.findViewById(R.id.decrease_button)
        val increaseButton: ImageButton = view.findViewById(R.id.increase_button)
        val totalPriceTextView: TextView = view.findViewById(R.id.total_price)
    }
}
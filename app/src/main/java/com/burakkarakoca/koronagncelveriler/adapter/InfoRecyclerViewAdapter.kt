package com.burakkarakoca.koronagncelveriler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.burakkarakoca.koronagncelveriler.R
import com.burakkarakoca.koronagncelveriler.model.TurkeyInfo
import java.util.*
import kotlin.collections.ArrayList

class InfoRecyclerViewAdapter() : RecyclerView.Adapter<InfoRecyclerViewAdapter.RowHolder>(){

    companion object{
        lateinit var exampleList: MutableList<TurkeyInfo>
    }

    constructor(exampleList: ArrayList<TurkeyInfo>) : this() {
        InfoRecyclerViewAdapter.exampleList = exampleList
//        InfoRecyclerViewAdapter.exampleList = InfoRecyclerViewAdapter.exampleList.asReversed()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.info_recyclerview_item,
            parent, false)

        return RowHolder(itemView)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.bindModel(exampleList[position],position)

    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val dateText = itemView.findViewById<TextView>(R.id.info_item_tarih_textview)
        private val vakaText = itemView.findViewById<TextView>(R.id.info_item_vaka_textview)
        private val olumText = itemView.findViewById<TextView>(R.id.info_item_olum_textview)
        private val tedaviText = itemView.findViewById<TextView>(R.id.info_item_tedavi_textview)

        fun bindModel(turkeyInfo: TurkeyInfo, position : Int){

            dateText.text = turkeyInfo.date
            vakaText.text = ""+turkeyInfo.confirmed
            olumText.text = ""+turkeyInfo.deaths
            tedaviText.text = ""+turkeyInfo.recovered

        }

    }

}
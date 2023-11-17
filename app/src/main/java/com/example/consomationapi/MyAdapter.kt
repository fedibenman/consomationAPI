package com.example.consomationapi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class MyAdapter(private var myDataSet: List<offre>, val listener: OnItemClickListener):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var selectedPosition = -1
    private var SelectedView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.ligne,parent,false)
        return ViewHolder(vh)
    }

    override fun getItemCount(): Int {
        return myDataSet.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //fonction pour afficher les donnee selon la position du curseur
        var current = myDataSet[position]
        holder.codeView.text = current.societe.toString()
        holder.nbpostsView.text =current.nbposts.toString()
        holder.intituleView.text=current.intitule.toString()
        holder.paysView.text=current.pays.toString()
        holder.specialiteView.text= current.specialite.toString()
        holder.societeView.text= current.societe.toString()

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }



    inner  class ViewHolder(val itemview: View):
        RecyclerView.ViewHolder(itemview), View.OnClickListener {
        val codeView   =  itemView.findViewById(R.id.code) as TextView
        val intituleView  =  itemView.findViewById(R.id.intitule) as TextView
        val specialiteView  =  itemView.findViewById(R.id.specialite) as TextView
        val societeView =  itemView.findViewById(R.id.societe) as TextView
        val nbpostsView =  itemView.findViewById(R.id.nbposts) as TextView
        val paysView =  itemView.findViewById(R.id.pays) as TextView

        init {
            itemView.setOnClickListener(this)

        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            SelectedView?.setBackgroundColor(Color.WHITE)
            v?.setBackgroundColor(Color.RED)
            SelectedView = v  ;
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
                selectedPosition = position

            }
        }




    }

}
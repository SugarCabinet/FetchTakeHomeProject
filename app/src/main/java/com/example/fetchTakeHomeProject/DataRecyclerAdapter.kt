package com.example.fetchTakeHomeProject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/*
Mandatory custom recycler adapter, class requires a dataSet which is a list of DataObjects
then formats and and links each dataObject to a data_list_item
*/
class DataRecyclerAdapter(private val dataSet: List<DataObject>) :
    RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_data_list_item, viewGroup, false)

        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(dataViewHolder: DataViewHolder, position: Int) {
        val currentItem = dataSet[position]
        dataViewHolder.dataId.text = currentItem.id.toString()
        dataViewHolder.dataListId.text = currentItem.listId.toString()
        dataViewHolder.dataName.text = currentItem.name

    }

    override fun getItemCount():Int {
        return dataSet.size
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataId:TextView = itemView.findViewById(R.id.dataId)
        val dataListId:TextView = itemView.findViewById(R.id.dataListId)
        val dataName:TextView = itemView.findViewById(R.id.dataName)

    }
}


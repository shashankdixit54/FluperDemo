package com.example.fluperdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fluperdemo.R
import com.example.fluperdemo.model.SpinnerStateModel

class ColorSpinnerAdapter(private val context: Context, modelArrayList: ArrayList<SpinnerStateModel>) : BaseAdapter() {

    public var modelArrayList: ArrayList<SpinnerStateModel>

    init {
        this.modelArrayList = modelArrayList
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return modelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return modelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_spinner, null, true)

            holder.checkBox = convertView!!.findViewById(R.id.checkbox) as CheckBox
            holder.tvAnimal = convertView.findViewById(R.id.text) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }


        holder.tvAnimal!!.text  = modelArrayList[position].title
        holder.checkBox!!.isChecked = modelArrayList[position].selected

        holder.checkBox!!.setTag(R.integer.btnplusview, convertView)
        holder.checkBox!!.tag = position
        holder.checkBox!!.setOnClickListener {

            val pos = holder.checkBox!!.tag as Int

            if (modelArrayList[pos].selected) {
                modelArrayList[pos].selected = false
                public_modelArrayList = modelArrayList
            } else {
                modelArrayList[pos].selected  = true
                public_modelArrayList = modelArrayList
            }
        }

        return convertView
    }

    private inner class ViewHolder {

        var checkBox: CheckBox? = null
        var tvAnimal: TextView? = null

    }

    companion object {
        lateinit var public_modelArrayList: ArrayList<SpinnerStateModel>
    }

}
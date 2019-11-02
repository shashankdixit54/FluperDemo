package com.example.fluperdemo.adapter


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fluperdemo.Constant
import com.example.fluperdemo.R
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.view.ProductDetailActivity
import java.io.File


class AllProductsListAdapter(
    internal var modelList: List<Product>,
    private val width: Int,
    private val context: Context
) : RecyclerView.Adapter<AllProductsListAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.titleTv.text = modelList[position].name
        val file = File(modelList[position].image)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath);
            holder.imageView.setImageBitmap(bitmap)
        }
        holder.descriptionTv.text = modelList[position].description
        holder.mainLinLayout.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(Constant.sendObject, modelList[position])
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var titleTv: TextView
        internal var imageView: ImageView
        internal var descriptionTv: TextView
        internal var mainLinLayout: LinearLayout

        init {
            titleTv = v.findViewById(R.id.titleTv) as TextView
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, (width / 70).toFloat())
            imageView = v.findViewById(R.id.hotelIv) as ImageView
            imageView.layoutParams.width = width / 4
            imageView.layoutParams.height = width / 4

            descriptionTv = v.findViewById(R.id.tvDescription) as TextView
            mainLinLayout = v.findViewById(R.id.mainLayout) as LinearLayout


        }
    }
}

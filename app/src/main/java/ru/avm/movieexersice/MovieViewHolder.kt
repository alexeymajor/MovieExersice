package ru.avm.movieexersice

import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(movieItem: View) : RecyclerView.ViewHolder(movieItem) {
    private val titleTv: TextView = movieItem.findViewById(R.id.movieItemTitle)
    private val imageIv: ImageView = movieItem.findViewById(R.id.movieItemImage)
    private val detailsBtn: Button = movieItem.findViewById(R.id.movieItemButton)
    private val favCb: CheckBox = movieItem.findViewById(R.id.movieItemFav)

    fun bind(title: String, resId: Int, isFav: Boolean, onDetailsListener: View.OnClickListener?, onFav: CompoundButton.OnCheckedChangeListener?) {
        titleTv.text = title
        imageIv.setImageResource(resId)
        favCb.setOnCheckedChangeListener(null)
        favCb.isChecked = isFav
        favCb.setOnCheckedChangeListener(onFav)

        Log.d("asdlakjshdlas", "ASda`111123123131sdasd")

        onDetailsListener?.let {
            Log.d("asdlakjshdlas", "ASdasdasd")
            detailsBtn.setOnClickListener(it)
        }
    }
}
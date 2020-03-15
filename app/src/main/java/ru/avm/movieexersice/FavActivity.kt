package ru.avm.movieexersice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initRecycler()
    }

    private fun initRecycler() {
        val recycler = findViewById<RecyclerView>(R.id.favoriteItemsRecycler)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = FavAdapter(this)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager

        val decoration = FavItemDecoration(this)
        recycler.addItemDecoration(decoration)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(recycler)
    }

    class FavItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(10,10,10,10)
        }

        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(canvas, parent, state)
            parent.layoutManager?.let { layoutManager ->
                for (i in 0 until parent.childCount) {
                    val child = parent.getChildAt(i)
                    canvas.drawRect(
                        layoutManager.getDecoratedLeft(child).toFloat(),
                        layoutManager.getDecoratedTop(child).toFloat(),
                        layoutManager.getDecoratedRight(child).toFloat(),
                        layoutManager.getDecoratedBottom(child).toFloat(),
                        Paint().apply {
                            color = context.resources.getColor(R.color.colorAccent)
                            style = Paint.Style.STROKE
                            strokeWidth = 10F
                        }
                    )
                }
            }
        }

    }

}

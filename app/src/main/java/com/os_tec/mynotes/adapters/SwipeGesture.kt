package com.os_tec.mynotes.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.mynotes.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


 class SwipeGesture(val activity:Activity,val adapter: NotesAdapter):ItemTouchHelper.SimpleCallback(ACTION_STATE_SWIPE,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private var swipeBack=false
    private val leftBackground=ContextCompat.getColor(activity,R.color.white)
    private val leftIcon= R.drawable.ic_delete
    private val rightBackground=ContextCompat.getColor(activity,R.color.white)
    private val rightIcon=R.drawable.ic_share
    var mdX=0f
    var mIsActive=true

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

     override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
         TODO("Not yet implemented")
     }

     override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

       val builder= RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addBackgroundColor(ContextCompat.getColor(activity, R.color.white))
            .addSwipeLeftBackgroundColor(leftBackground)
            .addSwipeLeftActionIcon(leftIcon)
            .addSwipeRightBackgroundColor(rightBackground)
            .addSwipeRightActionIcon(rightIcon)
            builder.create().decorate()



        mdX=dX //equal Value----------

        if (mdX >=1f){
            if (mdX<=200f){
                builder.addBackgroundColor(ContextCompat.getColor(activity,R.color.yellow))
                builder.create().decorate()
            }else{
                //mdX=300f
                builder.addBackgroundColor(ContextCompat.getColor(activity,R.color.green))
                builder.create().decorate()
            }
            Log.e("ActionState","Left")
            Log.e("ActionState","Action:$actionState:::Active:$isCurrentlyActive:::mXd:$mdX")

        }else if (mdX<-1f) {

            if (mdX >= -200f) {
                builder.addBackgroundColor(ContextCompat.getColor(activity, R.color.yellow))
                builder.create().decorate()
            } else {
                builder.addBackgroundColor(ContextCompat.getColor(activity, R.color.red))
                builder.create().decorate()
                if (!isCurrentlyActive){
                    adapter.deleteNotes(viewHolder.adapterPosition)
                    Log.e("ActionState","RightDelete...................")
                }
            }
            Log.e("ActionState","Right")
            Log.e("ActionState","Action:$actionState:::Active:$isCurrentlyActive:::mXd:$mdX")



    }
        setTouchListener(c, recyclerView, viewHolder, mdX, dY, actionState, isCurrentlyActive);


        super.onChildDraw(c, recyclerView, viewHolder, mdX, dY, actionState, isCurrentlyActive)
    }


    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }


    @SuppressLint("ClickableViewAccessibility")
    fun setTouchListener( c: Canvas,
                          recyclerView: RecyclerView,
                          viewHolder: RecyclerView.ViewHolder,
                          dX: Float,
                          dY: Float,
                          actionState: Int,
                          isCurrentlyActive: Boolean) {

        recyclerView.setOnTouchListener { view, event ->
           swipeBack = event!!.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            false
        }

    }

}
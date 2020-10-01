package com.example.mystore.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mystore.R
import com.example.mystore.utils.Anim
import kotlinx.android.synthetic.main.menu.*
import java.util.*

class MenuFragment : SmartFragment(R.layout.menu), OnTouchListener, OnGlobalLayoutListener {

    private val imgs = ArrayList<ImageView?>()
    private val txts = ArrayList<TextView?>()
    private var listener: Listener? = null
    private var opened = false
    private var curIndex = 0
    private var previousIndex = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onReady() {
        imgs.add(img1)
        imgs.add(img2)
        imgs.add(img3)
        imgs.add(img4)
        imgs.add(img5)

        txts.add(txt1)
        txts.add(txt2)
        txts.add(txt3)
        txts.add(txt4)
        txts.add(txt5)

        onModeChanged()

        bg.setOnTouchListener(this)

        requireView().viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        requireView().viewTreeObserver.removeOnGlobalLayoutListener(this)
        cont.visibility = View.GONE
    }

    fun setPreviousCursor() {
        if (curIndex == 0) setCursor(previousIndex)
    }

    fun setCursor(index: Int) {
        if (curIndex == index) return
        val context = context ?: return
        val sect = bg.width.toFloat() / 5
        val offset = cursor.width / 2
        val fromX = (curIndex + 0.5f) * sect - offset
        val toX = (index + 0.5f) * sect - offset
        val translateAnimation = TranslateAnimation(Animation.ABSOLUTE, fromX, Animation.ABSOLUTE, toX, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
        translateAnimation.fillAfter = true
        translateAnimation.duration = if (opened) 200L else 1L
        translateAnimation.interpolator = DecelerateInterpolator()
        cursor.startAnimation(translateAnimation)

        for (i in imgs.indices) {
            imgs[i]!!.setColorFilter(
                if (i == index) Color.WHITE else ContextCompat.getColor(context, R.color.menuGray))
            txts[i]!!.setTextColor(
                if (i == index) Color.WHITE else ContextCompat.getColor(context, R.color.menuGray))
        }

        previousIndex = curIndex
        curIndex = index
    }

    fun onModeChanged() {
        val change = false

//        txts.get(1).setText(change ? R.string.menu_delegate_raffles : R.string.menu_player_map);
//        txts.get(2).setText(change ? R.string.menu_delegate_add : R.string.menu_player_raffles);
//
//        imgs.get(1).setImageResource(change ? R.drawable.ico_menu_present : R.drawable.ico_menu_map);
//        imgs.get(2).setImageResource(change ? R.drawable.ico_menu_add : R.drawable.ico_menu_present);
//
//        cursor.setImageResource(change ? R.drawable.bg_circle_blue : R.drawable.bg_circle_green);
    }

    fun show(s: Boolean) {
        if (s) show() else hide()
    }

    fun show() {
        if (opened) return
//        MainFragment.enableSideMenu()
        cont.visibility = View.VISIBLE
        cont.startAnimation(Anim.getAnimation(context, R.anim.menu_show))
        opened = true
    }

    fun hide() {
        if (!opened) return
//        MainFragment.disableSideMenu()
        cont.startAnimation(Anim.getAnimation(context, R.anim.menu_hide) {
            cont.clearAnimation()
            cont.visibility = View.GONE
        })
        opened = false
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action != MotionEvent.ACTION_DOWN) return false
        val k = motionEvent.x / bg.width
        val index = (k * 5).toInt()
        listener?.onMenuClick(index)
        return true
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onMenuClick(index: Int)
    }
}
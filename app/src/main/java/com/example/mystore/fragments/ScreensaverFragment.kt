package com.example.mystore.fragments

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.mystore.R
import com.example.mystore.utils.Anim
import com.example.mystore.utils.Fonts
import kotlinx.android.synthetic.main.fragment_screensaver.*
import java.util.*

class ScreensaverFragment : SmartFragment(R.layout.fragment_screensaver), View.OnClickListener {

    private val imgs = ArrayList<ImageView?>()
    private var timer: Timer? = null
    private var visible = false
    private var turn = 0

    override fun onReady() {
        imgs.add(img1)
        imgs.add(img2)
        imgs.add(img3)
        imgs.add(img4)

        title.typeface = Fonts.getFuturaPtBook()
        cont.setOnClickListener(this)
        hide()
    }

    fun show(titleText: String?, full: Boolean) {
        bg?.post {
            if (visible) {
                return@post
            }
            visible = true
            this.title.text = titleText ?: ""
            if (context != null) this.title.startAnimation(Anim.getAppearSlide(context, 1000))
            for (i in imgs.indices) {
                imgs[i]?.setImageResource(R.drawable.img_anim_wait)
            }
            for (i in imgs.indices) {
                imgs[i]?.visibility = View.GONE
            }
            cont.visibility = View.VISIBLE
            if (full) {
                cont.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                bg_full.visibility = View.VISIBLE
                cont_image.visibility = View.VISIBLE
            } else {
                cont.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                bg_full.visibility = View.GONE
                cont_image.visibility = View.GONE
            }
            turn = 0
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    bg.post {
                        val context = context
                        if (context == null) {
                            hide()
                            return@post
                        }
                        if (turn >= imgs.size) {
                            if (timer != null) {
                                timer?.cancel()
                                timer = null
                            }
                            return@post
                        }
                        val img = imgs[turn]
                        img?.startAnimation(Anim.getWait(getContext()))
                        img?.visibility = View.VISIBLE
                        turn++
                    }
                }
            }, 0, 2000)
        }
    }

    fun hide() {
        bg?.post {
            if (timer != null) {
                timer?.cancel()
                timer = null
            }

            bg.clearAnimation()
            title.clearAnimation()

            for (i in imgs.indices) {
                imgs[i]?.clearAnimation()
            }

            cont.visibility = View.GONE
            visible = false
        }
    }

    override fun onClick(view: View) {}
}
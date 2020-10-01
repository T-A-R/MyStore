package com.example.mystore.fragments

import android.content.Intent
import android.util.Log
import android.view.View
import com.example.mystore.R
import com.example.mystore.activities.MainActivity
import com.example.mystore.utils.Anim
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.notification.*
import java.util.concurrent.TimeUnit

class NotificationFragment : SmartFragment(R.layout.notification) {

    private var positive = true
    private var isClosed = true
    private var sliderDisposable: Disposable? = null

    override fun onReady() {
        tv_notification.isSelected = true
        cont.setOnClickListener { textPress() }
        img_close_ring.setOnClickListener { textPress() }
    }

    fun setData(txtNotification: String, action: String?, eventID: String?) {
        if (action == "success") positive = true
        if (action == "incorrect") positive = false
        if (action == "account_activated") {
            MainFragment.wasNotify = MainFragment.NotifyType.Activated
            positive = true
        }

        isClosed = false

        val thread: Thread = object : Thread() {
            override fun run() {
                synchronized(this) {
                    activity?.runOnUiThread {
                        tv_notification?.text = txtNotification
                        if (positive) {
                            Log.d("T-L.NotificationFragmen", "Notification positive: $txtNotification")
                            cont?.visibility = View.VISIBLE
                            cont?.startAnimation(
                                Anim.getAnimation(
                                    context,
                                    R.anim.show_notification
                                )
                            )
                            cont?.setBackgroundResource(R.drawable.notification_green)
                            img_close_ring?.setBackgroundResource(R.drawable.circle_yellow)
                            img_close_crest?.setBackgroundResource(R.drawable.ico_x)
                        } else {
                            Log.d("T-L.NotificationFragmen", "Notification negative: $txtNotification")
                            cont?.visibility = View.VISIBLE
                            cont?.startAnimation(
                                Anim.getAnimation(
                                    context,
                                    R.anim.show_notification
                                )
                            )
                            cont?.setBackgroundResource(R.drawable.notification_orange)
                            img_close_ring?.setBackgroundResource(R.drawable.circle_green)
                            img_close_crest?.setBackgroundResource(R.drawable.ico_close_white)
                        }
                        tv_notification?.isSelected = true
                        runRxSlider()
                    }
                }
            }
        }
        thread.start()
    }

    fun hide() {
        cont?.visibility = View.GONE
        isClosed = true
    }

    private fun runRxSlider() {
        Observable.interval(20, TimeUnit.SECONDS).take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {

                override fun onSubscribe(d: Disposable) {
                    sliderDisposable = d
                }

                override fun onNext(aLong: Long) {
                    if (!isClosed) {
                        cont?.startAnimation(Anim.getAnimation(context, R.anim.hide_notification))
                        cont?.visibility = View.GONE
                        isClosed = true
                        //TODO Something
                    }
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    fun cancel() {
        if (sliderDisposable != null) sliderDisposable!!.dispose()
    }

    fun textPress() {
        isClosed = true
        val thread: Thread = object : Thread() {
            override fun run() {
                synchronized(this) { activity?.runOnUiThread { hide() } }
            }
        }
        thread.start()
    }

    fun closePress() {
        if (!isClosed) {
            cont!!.startAnimation(Anim.getAnimation(context, R.anim.hide_notification))
            cont!!.visibility = View.GONE
            isClosed = true
            //TODO Something
        }
    }

    private fun restartActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
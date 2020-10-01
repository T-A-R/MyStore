package com.example.mystore.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.mystore.BuildConfig
import com.example.mystore.R
import com.example.mystore.database.StoreDao
import com.example.mystore.database.StoreDatabase
import com.example.mystore.fragments.MainFragment
import com.example.mystore.utils.Fonts

class MainActivity : AppCompatActivity(), ViewTreeObserver.OnGlobalLayoutListener {

    private var mainFragment: MainFragment? = null
    private var mIsFirstStart = true

    companion object {
        @JvmField
        val TAG: String = "TARLOGS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fonts.init(this)

        if (mIsFirstStart || savedInstanceState == null) {
            mIsFirstStart = false
            mainFragment = supportFragmentManager.findFragmentById(R.id.main) as MainFragment?
            if (BuildConfig.DEBUG && mainFragment == null) {
                error("Assertion failed")
            }
            val view = mainFragment!!.view
            if (mainFragment == null || view == null)
                Log.d(TAG, "MainActivity.onCreate() WTF? view == null")
            else {
                view.post(Runnable {
                    view.viewTreeObserver.addOnGlobalLayoutListener(this@MainActivity)
                })
            }
        }
    }

    override fun onGlobalLayout() {
        val view: View = mainFragment?.getView() ?: return
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        view.post { mainFragment?.startScreens() }
    }

    fun getDao(): StoreDao {
        return StoreDatabase.getInstance(this).storeDao
    }
}
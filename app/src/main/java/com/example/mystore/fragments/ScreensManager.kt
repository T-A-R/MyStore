package com.example.mystore.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.mystore.R
import com.example.mystore.fragments.SmartFragment.ScreenListener

class ScreensManager internal constructor(
    private val activity: AppCompatActivity,
    private val main: IMainFragment?
) : ScreenListener {
    private var listener: Listener? = null
    var curFragment: SmartFragment? = null
        private set

    @JvmOverloads
    fun openScreen(fragment: SmartFragment?, fromBackPress: Boolean = false) {
        if (main == null) return
        fragment!!.main = main
        fragment.screenListener = this
        removeFragment(fromBackPress)
        try {
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            if (fromBackPress) {
                transaction.setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            } else {
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
            }
            transaction.addToBackStack(null)
            transaction.add(R.id.cont_screen, fragment).commit()
            curFragment = fragment
            listener?.onOpenScreen(fragment)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun removeFragment(fromBackPress: Boolean) {
        if (curFragment == null) return

//        curFragment.setListener(null); //TODO Проверить!
        curFragment!!.hideKeyboard()
        try {
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            if (fromBackPress) transaction.setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            ) else transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
            transaction.addToBackStack(null)
            transaction.remove(curFragment!!).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        curFragment = null
        System.gc()
        main!!.hideScreensaver()
    }

    override fun fragmentReplace(newScreen: SmartFragment?, fromBackPress: Boolean) {
        openScreen(newScreen, fromBackPress)
    }

    fun onBackPressed(): Boolean {
        return curFragment != null && curFragment!!.onBackPressed()
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onOpenScreen(screen: SmartFragment?)
    }
}
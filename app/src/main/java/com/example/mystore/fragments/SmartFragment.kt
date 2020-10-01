package com.example.mystore.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mystore.activities.MainActivity
import com.example.mystore.database.StoreDao

abstract class SmartFragment(var layoutSrc: Int) : Fragment() {

    private var eventsListener: Events? = null
    var main: IMainFragment? = null
    var screenListener: ScreenListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutSrc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        onReady()
    }

    protected abstract fun onReady()

    open fun getMainActivity(): MainActivity? {
        return activity as MainActivity?
    }

    open fun <T : View?> findViewById(id: Int): T? {
        val view = view
        return view?.findViewById(id)
    }

    override fun getContext(): Context? {
        val view = view
        return view?.context
    }

    open fun hideKeyboard() {
        val context = context ?: return
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    open fun isMenuShown(): Boolean {
        return false
    }

    fun getDao(): StoreDao {
        return getMainActivity()!!.getDao()
    }

    open fun showToast(text: String) {
        val activity = getMainActivity()
        try {
            activity?.runOnUiThread { Toast.makeText(activity, text, Toast.LENGTH_SHORT).show() }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun showScreensaver(titleId: Int, full: Boolean) {
        val title: String = resources.getString(titleId)
        showScreensaver(title, full)
    }

    open fun showScreensaver(full: Boolean) {
        showScreensaver("", full)
    }

    open fun showScreensaver(title: String?, full: Boolean) {
        hideKeyboard()
        main?.showScreensaver(title, full)
    }

    open fun hideScreensaver() {
        main?.hideScreensaver()
    }

    open fun showMenu() {
        main?.showMenu()
    }

    open fun hideMenu() {
        main?.hideMenu()
    }

    open fun setMenuCursor(index: Int) {
        main?.setMenuCursor(index)
    }

    interface Events {
        fun runEvent(id: Int)
        // 01 -
        // 02 -
    }

    open fun setMyEventListener(listener: Events) {
        eventsListener = listener
    }

    interface ScreenListener {
        fun fragmentReplace(
            newScreen: SmartFragment?, fromBackPress: Boolean
        )
    }

    protected open fun replaceFragment(newScreen: SmartFragment) {
        view?.post { screenListener?.fragmentReplace(newScreen, false) }
    }

    protected open fun replaceFragmentBack(newScreen: SmartFragment) {
        view?.post { screenListener?.fragmentReplace(newScreen, true) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        hideScreensaver()
    }
}
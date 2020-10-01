package com.example.mystore.fragments

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.mystore.R
import kotlinx.android.synthetic.main.view_drawer.*

class MainFragment : SmartFragment(R.layout.fragment_main), View.OnClickListener,
    MenuFragment.Listener, ScreensManager.Listener, IMainFragment {

    var screensManager: ScreensManager? = null
        private set
    private var menu: MenuFragment? = null
    private var screensaver: ScreensaverFragment? = null
    private var isSideMenuOpen = false

    override fun onReady() {

        menu = childFragmentManager.findFragmentById(R.id.frag_menu) as MenuFragment?
        screensaver = childFragmentManager.findFragmentById(R.id.frag_saver) as ScreensaverFragment?
        notificationFragment =
            childFragmentManager.findFragmentById(R.id.frag_notification) as NotificationFragment?
        screensManager = ScreensManager((activity as AppCompatActivity?)!!, this)
        sideMenuDrawer = findViewById<View>(R.id.drawer_cont) as DrawerLayout?
        enableSideMenu()
        sideMenuDrawer?.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                isSideMenuOpen = true
                menu?.setCursor(0)
            }

            override fun onDrawerClosed(drawerView: View) {
                isSideMenuOpen = false
                menu?.setPreviousCursor()
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })

        menu?.setListener(this)
        screensManager?.setListener(this)

        menu1.setOnClickListener { onSideMenu1Click() }
        menu2.setOnClickListener { onSideMenu2Click() }
        menu3.setOnClickListener { onSideMenu3Click() }
        menu4.setOnClickListener { onSideMenu4Click() }
        menu5.setOnClickListener { onSideMenu5Click() }
        exit_btn.setOnClickListener { onSideMenuExitClick() }
    }

    override fun onResume() {
        super.onResume()
        sideMenuDrawer = findViewById<View>(R.id.drawer_cont) as DrawerLayout?
    }

    fun startScreens() {
        openScreen(TempFragment())
    }

    fun openNewAcivityScreen() {
        openScreen(newActivityScreen ?: return)
    }

    private fun openScreen(screen: SmartFragment?) {
        newActivityScreen = null
        screensManager?.openScreen(screen)
    }

    private fun openScreen(screen: SmartFragment, force: Boolean) {
        screensManager?.openScreen(screen, force)
    }

    override fun onMenuClick(index: Int) {
        when (index) {
            0 -> {
                menu?.setCursor(index)
                onMenu1Press()
            }
            1 -> {
                menu?.setCursor(index)
                onMenu2Press()
                hide()
            }
            2 -> {
                menu?.setCursor(index)
                onMenu3Press()
                hide()
            }
            3 -> {
                menu?.setCursor(index)
                onMenu4Press()
                hide()
            }
            4 -> {
                menu?.setCursor(index)
                onMenu5Press()
                hide()
            }
        }
    }

    private fun onMenu1Press() {
        if (!sideMenuDrawer!!.isDrawerOpen(Gravity.LEFT)) {
            show()
        } else {
            hide()
        }
    }

    private fun onMenu2Press() {}
    private fun onMenu3Press() {}
    private fun onMenu4Press() {}
    private fun onMenu5Press() {}

    private fun onSideMenu1Click() {}
    private fun onSideMenu2Click() {}
    private fun onSideMenu3Click() {}
    private fun onSideMenu4Click() {}
    private fun onSideMenu5Click() {}
    private fun onSideMenuExitClick() {
        hide()
    }

    override fun onOpenScreen(screen: SmartFragment?) {
        menu?.show(screen!!.isMenuShown())
        if (screen is TempFragment) {
            menu?.setCursor(1)
        } else if (screen is TempFragment) {
            menu?.setCursor(2)
        } else if (screen is TempFragment) {
            menu?.setCursor(3)
        } else if (screen is TempFragment) {
            menu?.setCursor(4)
        } else if (screen is TempFragment) {
            menu?.setCursor(4)
        }
    }

    override fun showScreensaver(title: String?, full: Boolean) {
        screensaver?.show(title, full)
    }

    override fun hideScreensaver() {
        screensaver?.hide()
    }

    override fun showMenu() {
        menu?.show()
    }

    override fun hideMenu() {
        menu?.hide()
    }

    override fun setMenuCursor(index: Int) {
        menu?.setCursor(index)
    }

    override fun onBackPressed(): Boolean {
        return if (sideMenuDrawer!!.isDrawerOpen(Gravity.LEFT)) {
            hide()
            true
        } else screensManager?.onBackPressed() ?: return true
    }

    enum class NotifyType {
        Activated
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        //TODO ON LEFT MENU CLICK
    }

    private fun hide() {
        if (isSideMenuOpen) {
            isSideMenuOpen = false
            sideMenuDrawer?.closeDrawer(Gravity.LEFT)
        }
    }

    fun show() {
        if (!isSideMenuOpen) {
            isSideMenuOpen = true
            sideMenuDrawer?.openDrawer(Gravity.LEFT)
        }
    }

    companion object {
        var wasNotify: NotifyType? = null
        var newActivityScreen: SmartFragment? = null
        private var notificationFragment: NotificationFragment? = null
        private var sideMenuDrawer: DrawerLayout? = null
        private var mHomeBtnCont: RelativeLayout? = null
        private var mSyncBtnCont: RelativeLayout? = null
        private var mQuotaBtnCont: RelativeLayout? = null

        fun disableSideMenu() {
            sideMenuDrawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        fun enableSideMenu() {
            sideMenuDrawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

        fun showNotification(notification: String, action: String, eventID: String) {
            notificationFragment?.setData(notification, action, eventID)
        }

        fun openDrawer() {
            sideMenuDrawer?.openDrawer(Gravity.LEFT)
        }
    }
}
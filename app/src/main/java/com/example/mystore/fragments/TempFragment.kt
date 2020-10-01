package com.example.mystore.fragments

import android.util.Log
import com.example.mystore.R
import com.example.mystore.activities.MainActivity.Companion.TAG
import com.example.mystore.utils.Anim
import com.example.mystore.utils.Fonts
import kotlinx.android.synthetic.main.fragment_temp.*

class TempFragment : SmartFragment(R.layout.fragment_temp) {

    override fun onReady() {
        initViews()
        MainFragment.enableSideMenu()

        btn_next.setOnClickListener { onNextButtonClick() }

    }

    fun initViews() {
        et_temp.typeface = Fonts.getFuturaPtMedium()
        btn_next.typeface = Fonts.getFuturaPtBook()
        btn_next.transformationMethod = null
        cont_temp_fragment.startAnimation(Anim.getAppear(context))
        btn_next.startAnimation(Anim.getAppearSlide(context, 500))
    }

    fun onNextButtonClick() {
        Log.d(TAG, "onNextButtonClick: ")
        showMenu()
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}
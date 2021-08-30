package com.soft.eventos.utils

import android.view.View
import com.soft.eventos.MainActivity
import kotlinx.android.synthetic.main.details_events_fragment.*

class UtilChecking(private val mainActivity: MainActivity) {

    fun loadingCheking(){
        mainActivity.include_loading_checking.visibility = View.VISIBLE
        mainActivity.include_details_events.visibility = View.GONE
        mainActivity.include_success_checking.visibility = View.GONE
        mainActivity.include_error_checking.visibility = View.GONE
    }

    fun successCheking(){
        mainActivity.include_loading_checking.visibility = View.GONE
        mainActivity.include_details_events.visibility = View.GONE
        mainActivity.include_success_checking.visibility = View.VISIBLE
        mainActivity.include_error_checking.visibility = View.GONE
    }

    fun errorCheking(){
        mainActivity.include_loading_checking.visibility = View.GONE
        mainActivity.include_details_events.visibility = View.GONE
        mainActivity.include_success_checking.visibility = View.GONE
        mainActivity.include_error_checking.visibility = View.VISIBLE
    }

    fun clickTryAgainChecking(){
        mainActivity.include_details_events.visibility = View.VISIBLE
        mainActivity.include_error_checking.visibility = View.GONE
        mainActivity.include_loading_checking.visibility = View.GONE
        mainActivity.include_success_checking.visibility = View.GONE
    }
}
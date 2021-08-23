package com.soft.eventos.utils

import android.view.View
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.list_events_fragment.*

class UtilList(val mainFragment: FragmentActivity) {

    fun setLoading() {
        mainFragment.progressBar.visibility = View.VISIBLE
        mainFragment.recyclerView.visibility = View.GONE
        mainFragment.layoutErrorList.visibility = View.GONE
    }

    fun setSuccess() {
        mainFragment.recyclerView.visibility = View.VISIBLE
        mainFragment.progressBar.visibility = View.GONE
        mainFragment.layoutErrorList.visibility = View.GONE
    }

    fun setError() {
        mainFragment.layoutErrorList.visibility = View.VISIBLE
        mainFragment.recyclerView.visibility = View.GONE
        mainFragment.progressBar.visibility = View.GONE
    }
}
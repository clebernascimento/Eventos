package com.soft.eventos.ui.detailsEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soft.eventos.MainActivity
import com.soft.eventos.databinding.DetailsEventsFragmentBinding
import com.soft.eventos.databinding.ListEventsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsEventsFragment : Fragment() {

    private lateinit var binding: DetailsEventsFragmentBinding
    private val viewModel: DetailsEventsViewModel by viewModel()
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = with(DetailsEventsFragmentBinding.inflate(inflater)) {
        lifecycleOwner = viewLifecycleOwner
//        viewModel = this@DetailsEventsFragment.viewModel
        binding = this
        root
    }


    /**
     * Inicializa os metodos
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
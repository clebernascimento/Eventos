package com.soft.eventos.ui.detailsEvents

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.soft.eventos.MainActivity
import com.soft.eventos.R
import com.soft.eventos.data.model.Events
import com.soft.eventos.databinding.DetailsEventsFragmentBinding
import com.soft.eventos.utils.DateTime
import kotlinx.android.synthetic.main.details_events_fragment.*
import kotlinx.android.synthetic.main.item_events.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsEventsFragment : Fragment() {

    private lateinit var binding: DetailsEventsFragmentBinding
    private val viewModel: DetailsEventsViewModel by viewModel()
    private lateinit var mainActivity: MainActivity

    private val args: DetailsEventsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = with(DetailsEventsFragmentBinding.inflate(inflater)) {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@DetailsEventsFragment.viewModel
        binding = this
        root
    }

    /**
     * Inicializa os metodos
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgsFragment()
    }

    @SuppressLint("SetTextI18n")
    private fun setArgsFragment() {
        args.detailsEvents?.let { events ->
            binding.apply {
                nameEvents.text = events.title
                Glide.with(imgEvents.context)
                    .load(events.image)
                    .into(imgEvents)
                dateEvents.text =
                    "${getString(R.string.text_date)} " + DateTime.getDate(events.date)
                priceEvents.text = "${getString(R.string.text_cifrao)} " + events.price
                descriptionEvents.text = events.description
            }
        }
    }
}
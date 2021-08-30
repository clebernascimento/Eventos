package com.soft.eventos.ui.detailsEvents

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.soft.eventos.MainActivity
import com.soft.eventos.R
import com.soft.eventos.data.model.CheckingEvents
import com.soft.eventos.databinding.DetailsEventsFragmentBinding
import com.soft.eventos.utils.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.details_events_fragment.*
import kotlinx.android.synthetic.main.include_details_events.*
import kotlinx.android.synthetic.main.include_details_events.view.*
import kotlinx.android.synthetic.main.include_error_checking.*
import kotlinx.android.synthetic.main.include_error_checking.view.*
import kotlinx.android.synthetic.main.include_success_checking.view.*
import kotlinx.android.synthetic.main.item_events.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsEventsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: DetailsEventsFragmentBinding
    private val viewModel: DetailsEventsViewModel by viewModel()
    private lateinit var mainActivity: MainActivity
    private lateinit var utilChecking: UtilChecking

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
        clickChecking()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            val list = it as MainActivity
            utilChecking = UtilChecking(list)
            mainActivity = requireActivity() as MainActivity
            include_success_checking.finishChecking.setOnClickListener(this)
            include_error_checking.tryAgainChecking.setOnClickListener(this)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setArgsFragment() {
        args.detailsEvents?.let { events ->
            binding.apply {
                includeDetailsEvents.nameEvents.text = events.title
                Glide.with(imgEvents.context)
                    .load(events.image)
                    .into(includeDetailsEvents.imgEvents)
                includeDetailsEvents.dateEvents.text =
                    "${getString(R.string.text_date)} " + DateTime.getDate(events.date)
                includeDetailsEvents.priceEvents.text = "${getString(R.string.text_cifrao)} " + events.price
                includeDetailsEvents.descriptionEvents.text = events.description
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.finishChecking -> {
                hideKeyboard()
                clearFields()
                findNavController().popBackStack()
            }

            R.id.tryAgainChecking -> {
                hideKeyboard()
                clearFields()
                utilChecking.clickTryAgainChecking()
            }
        }
    }

    private fun sendChecking(name: String, email: String) {
        viewModel.sendChecking(name.trim(), email.trim()).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { utilChecking.successCheking() }
                }
                Status.ERROR -> utilChecking.errorCheking()
                Status.LOADING -> utilChecking.loadingCheking()
            }
        }
    }

    private fun clearFields() {
        binding.apply {
            includeDetailsEvents.editName.text?.clear()
            includeDetailsEvents.editEmail.text?.clear()
        }
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun clickChecking(){
        include_details_events.btnChekin.setOnClickListener {
            val name = include_details_events.editName.text.toString()
            val email = include_details_events.editEmail.text.toString()
            if (name.isEmpty() && email.isEmpty()) {
                Snackbar.make(requireView(), R.string.fildtext_empty, Snackbar.LENGTH_LONG).show()
            }
            else if (!name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*".toRegex())
                or !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(requireView(),getString(R.string.text_name_email_invalid), Snackbar.LENGTH_LONG).show()
            } else {
                sendChecking(name, email)
            }
        }
    }
}
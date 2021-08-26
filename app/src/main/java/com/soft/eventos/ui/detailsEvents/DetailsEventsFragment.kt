package com.soft.eventos.ui.detailsEvents

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.item_events.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
        clickChecking()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            val list = it as MainActivity
            mainActivity = (requireActivity() as MainActivity)
        }
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

    private fun sendChecking(checking: String, chek: String) {
        viewModel.sendChecking(checking.trim(), chek.trim()).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { check ->
                        clearFields()
                        hideKeyboard()
                        binding.progressBar.visibility = View.GONE
                        binding.scrollView.visibility = View.VISIBLE
                        viewModel.messageEventData.observe(viewLifecycleOwner) { stringId ->
                            Snackbar.make(requireView(), stringId, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    viewModel.messageEventData.observe(viewLifecycleOwner) { stringId ->
                        Snackbar.make(requireView(), stringId, Snackbar.LENGTH_LONG).show()
                    }
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE
                }
            }
        }
    }

    private fun clearFields() {
        binding.apply {
            editName.text?.clear()
            editEmail.text?.clear()
        }
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun clickChecking() {
        binding.btnChekin.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                sendChecking(name, email)
            } else {
                Snackbar.make(
                    requireView(),
                    "Campos Nome/E-mail não pode está fazios!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}
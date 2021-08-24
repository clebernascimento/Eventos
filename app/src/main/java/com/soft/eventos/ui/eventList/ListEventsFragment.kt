package com.soft.eventos.ui.eventList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.soft.eventos.BuildConfig
import com.soft.eventos.MainActivity
import com.soft.eventos.R
import com.soft.eventos.data.model.Events
import com.soft.eventos.databinding.ListEventsFragmentBinding
import com.soft.eventos.utils.Status
import com.soft.eventos.utils.UtilList
import com.soft.eventos.utils.navigateWithAnimations
import kotlinx.android.synthetic.main.list_events_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.FieldPosition

class ListEventsFragment : Fragment() {

    private lateinit var binding: ListEventsFragmentBinding
    private val viewModel: ListEventsViewModel by viewModel()
    private lateinit var adapter: EventsAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var utilList: UtilList
    private val listNameEvents: MutableList<Events> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = with(ListEventsFragmentBinding.inflate(inflater)) {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@ListEventsFragment.viewModel
        binding = this
        root
    }

    /**
     * Inicializa os metodos
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        setupUI()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            val list = it
            utilList = UtilList(list)
            mainActivity = (requireActivity() as MainActivity)
        }
    }

    /**
     * Metodo para criar o adapter do recyclerView
     */
    private fun setupUI() {
        adapter = EventsAdapter(listener = this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = this@ListEventsFragment.adapter
        }
    }

    /**
     * Metodo para fazer a requição do ViewModel
     */
    private fun setupObservers() {
        viewModel.getEvents().observe(viewLifecycleOwner, { allEvents ->
            allEvents.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        if (resources.data?.isNullOrEmpty() == true) {
                            utilList.setError()
                        } else {
                            utilList.setSuccess()
                            resources.data?.let(::listEvents)
                        }
                    }
                    Status.ERROR -> {
                        utilList.setError()
                    }
                    Status.LOADING -> {
                        utilList.setLoading()
                    }
                }
            }
        })
    }

    /**
     * Metodo para carregar os dados da requisição SUCCESS
     */
    private fun listEvents(event: List<Events>) {
        adapter.addEvents(event)
    }

    /**
     * Metodo para pesquisar eventos no search na toolbar
     */
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mainActivity.menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val version = menu.findItem(R.id.version)
        version.title = "${getString(R.string.text_version)} " + BuildConfig.VERSION_NAME
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.text_search_login)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Metodo de pesquisa dentro do recyclerView
     */
    private fun search(text: String?) {
        adapter.apply {
            listNameEvents.clear()
            listNameEvents.addAll(listEvents)
            listEvents.clear()
            text?.let {
                if (it.isEmpty()) {
                    setupObservers()
                } else {
                    listNameEvents.forEach { events ->
                        if (events.title.contains(text, true)) {
                            adapter.listEvents.add(events)
                        }
                    }
                    if (listNameEvents.isNullOrEmpty()) {
                        utilList.setError()
                    }
                }
                updateRecyclerView()
            }
        }
    }

    /**
     * Metodo para atualizar o recyclerView apos uma pesquisa no search
     */
    private fun updateRecyclerView() {
        recyclerView.apply {
            adapter!!.notifyDataSetChanged()
        }
    }

    fun onItemClick(events: Events?, position: Int) {
        if (events != null) {
            val directions = ListEventsFragmentDirections
                .actionListEventsFragmentToDetailsEventsFragment()
            findNavController().navigateWithAnimations(directions)
            adapter.notifyItemChanged(position)
        }
    }
}
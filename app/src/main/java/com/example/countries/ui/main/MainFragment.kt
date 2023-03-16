package com.example.countries.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.R
import com.example.countries.viewmodels.SharedViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // recyclerView init
        val manager = LinearLayoutManager(this.context)
        recyclerView = view.findViewById(R.id.recyclerView)
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.countriesData.observe(this, Observer
        {
            val adapter = MainRecyclerAdapter(requireContext(), it)
            recyclerView.adapter = adapter

            swipeLayout.isRefreshing = false
        })

        return view
    }


}

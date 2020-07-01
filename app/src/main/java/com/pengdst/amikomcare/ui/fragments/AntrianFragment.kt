package com.pengdst.amikomcare.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.FragmentAntrianBinding
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.listeners.RecyclerViewCallback
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter
import com.pengdst.amikomcare.ui.adapters.PasienAdapter
import com.pengdst.amikomcare.ui.viewmodels.AntrianViewModel

class AntrianFragment : Fragment(), RecyclerViewCallback {
    @Suppress("PrivatePropertyName")
    private val TAG = "AntrianFragment"

    lateinit var binding: FragmentAntrianBinding
    lateinit var antrianViewModel: AntrianViewModel
    lateinit var antrianAdapter: AntrianAdapter

    private fun initBinding(view: View) {
        binding = FragmentAntrianBinding.bind(view)
    }

    private fun setupListener() {

    }

    private fun observeViewModel() {
        antrianViewModel.observePasienList.observe(viewLifecycleOwner, Observer {
            if (it.status == RESULT_SUCCESS){
                antrianAdapter.list = it.data!!
                binding.tvTotalPasien.text = it.data!![0].id.toString()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = antrianAdapter
    }

    private fun setupViewComponent() {

    }

    private fun initVariable() {
        antrianAdapter = AntrianAdapter(this)
    }

    private fun fetchViewModel() {
        antrianViewModel.fetchAntrianList()
    }

    private fun initViewModel() {
        antrianViewModel = ViewModelProvider(this).get(AntrianViewModel::class.java)
    }

    private fun initAdapter() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initVariable()
        initAdapter()
        initViewModel()
        fetchViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        initBinding(inflater.inflate(R.layout.fragment_antrian, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewComponent()
        setupRecyclerView()
        observeViewModel()
        setupListener()
    }

    override fun onItemClick(view: View, antrian: AntrianModel) {

    }
}
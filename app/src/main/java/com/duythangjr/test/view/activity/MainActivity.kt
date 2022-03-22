package com.duythangjr.test.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.duythangjr.test.databinding.ActivityMainBinding
import com.duythangjr.test.util.Resource
import com.duythangjr.test.view.adapter.PopularAdapter
import com.duythangjr.test.viewmodel.AppRepository
import com.duythangjr.test.viewmodel.PopularViewModel
import com.duythangjr.test.viewmodel.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PopularViewModel
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.rcvPopular.setHasFixedSize(true)
        binding.rcvPopular.layoutManager = GridLayoutManager(this, 2)
        popularAdapter = PopularAdapter()
        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelProviderFactory(application, AppRepository())
        viewModel = ViewModelProvider(this, factory).get(PopularViewModel::class.java)
        getPicture()
    }

    private fun getPicture() {
        viewModel.popularList.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    response.data?.let {
                        popularAdapter.differ.submitList(it.results)
                        binding.rcvPopular.adapter = popularAdapter
                    }
                }

                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    response.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
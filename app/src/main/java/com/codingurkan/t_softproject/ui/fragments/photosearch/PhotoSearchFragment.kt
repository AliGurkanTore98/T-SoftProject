package com.codingurkan.t_softproject.ui.fragments.photosearch

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingurkan.t_softproject.R
import com.codingurkan.t_softproject.adapter.PhotoListAdapter
import com.codingurkan.t_softproject.base.BaseFragment
import com.codingurkan.t_softproject.databinding.FragmentPhotoSearchBinding
import com.codingurkan.t_softproject.model.Hit
import com.codingurkan.t_softproject.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoSearchFragment : BaseFragment<FragmentPhotoSearchBinding, PhotoSearchViewModel>(
    FragmentPhotoSearchBinding::inflate
) {
    override val viewModel by viewModels<PhotoSearchViewModel>()
    private lateinit var adapter : PhotoListAdapter

    override fun onCreateFinished() {
    Toast.makeText(requireContext(), WELCOME_SEARCH, Toast.LENGTH_SHORT).show()
    }
    override fun initializeListeners() {
        binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(text: String?): Boolean {
                    if (text != null){
                        viewModel.query.postValue(text)
                        searchView.clearFocus()
                    }
                    return true
                }
                override fun onQueryTextChange(text: String?): Boolean {
                    if (text != null){
                        viewModel.query.postValue(text)
                    }
                    return true
                }
            })
            viewModel.apply {
                binding.apply {
                    btnBack.setOnClickListener {
                        pageNumber.value = pageNumber.value?.plus(-1)
                    }
                    btnNext.setOnClickListener {
                        pageNumber.value = pageNumber.value?.plus(1)
                    }
                }
            }
        }
    }
    override fun observeEvents() {
        viewModel.searchPhotoList.observe(viewLifecycleOwner){
            initAdapters(it)
        }
        viewModel.query.observe(viewLifecycleOwner){ _query ->
            if (_query != null){
                viewModel.pageNumber.value?.let { _pageNumber ->
                    viewModel.downloadSearchPhoto(_query, _pageNumber, PER_PAGE)
                }
                viewModel.pageNumber.value = FIRST_PAGE
            }

        }
        viewModel.pageNumber.observe(viewLifecycleOwner){_pageNumber ->
            if(_pageNumber == 1){
                viewModel.query.value?.let { _query ->
                    viewModel.downloadSearchPhoto(_query, _pageNumber, PER_PAGE)
                }
                binding.btnBack.visibility = View.INVISIBLE
                binding.tvPage.text = _pageNumber.toString()
            }
            else{
                viewModel.query.value?.let { _query ->
                    viewModel.downloadSearchPhoto(_query, _pageNumber, PER_PAGE)
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.tvPage.text = _pageNumber.toString()
            }
        }
    }
    override fun initAdapters(data: List<Hit>) {
        adapter = PhotoListAdapter(data,object : PhotoListAdapter.ItemClickListener{
            override fun onClick(data: Hit) {
                val bundle = Bundle()
                bundle.putSerializable(PHOTO_DATA,data)
                findNavController().navigate(R.id.action_photoSearchFragment_to_photoDetailsFragment,bundle)
            }
        })
        binding.recyclerView.adapter = adapter
    }
}
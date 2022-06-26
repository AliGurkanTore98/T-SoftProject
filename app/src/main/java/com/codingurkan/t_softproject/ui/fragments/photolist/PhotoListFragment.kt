package com.codingurkan.t_softproject.ui.fragments.photolist


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingurkan.t_softproject.R
import com.codingurkan.t_softproject.adapter.PhotoListAdapter
import com.codingurkan.t_softproject.base.BaseFragment
import com.codingurkan.t_softproject.databinding.FragmentPhotoListBinding
import com.codingurkan.t_softproject.model.Hit
import com.codingurkan.t_softproject.util.FIRST_PAGE
import com.codingurkan.t_softproject.util.PHOTO_DATA
import com.codingurkan.t_softproject.util.PER_PAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoListFragment : BaseFragment<FragmentPhotoListBinding,PhotoListViewModel>(
    FragmentPhotoListBinding::inflate
) {
    override val viewModel by viewModels<PhotoListViewModel>()
    private lateinit var adapter: PhotoListAdapter

    override fun onCreateFinished() {
        viewModel.downloadPhotos(FIRST_PAGE, PER_PAGE)
    }
    override fun initializeListeners() {
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
    override fun observeEvents() {
       viewModel.photoList.observe(viewLifecycleOwner){
           if (it != null) {
               initAdapters(it)
           }
       }
        viewModel.pageNumber.observe(this) {_pageNumber ->
            if (_pageNumber == 1){
                binding.btnBack.visibility = View.INVISIBLE
                binding.tvPage.text = _pageNumber.toString()
                viewModel.downloadPhotos(_pageNumber,PER_PAGE)
            }
            else{
                binding.btnBack.visibility = View.VISIBLE
                binding.tvPage.text = _pageNumber.toString()
                viewModel.downloadPhotos(_pageNumber,PER_PAGE)

            }
        }
    }
    override fun initAdapters(data : List<Hit>) {
        adapter = PhotoListAdapter(data,object : PhotoListAdapter.ItemClickListener{
            override fun onClick(data: Hit) {
                val bundle = Bundle()
                bundle.putSerializable(PHOTO_DATA,data)
                findNavController().navigate(R.id.action_photoListFragment_to_photoDetailsFragment,bundle)
            }
        })
        binding.recyclerView.adapter = adapter
    }
}
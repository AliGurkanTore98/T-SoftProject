package com.codingurkan.t_softproject.ui.fragments.photodetails


import androidx.fragment.app.viewModels
import com.codingurkan.t_softproject.base.BaseFragment
import com.codingurkan.t_softproject.databinding.FragmentPhotoDetailsBinding
import com.codingurkan.t_softproject.model.Hit
import com.codingurkan.t_softproject.util.PHOTO_DATA
import com.codingurkan.t_softproject.util.loadImage


class PhotoDetailsFragment : BaseFragment<FragmentPhotoDetailsBinding, PhotoDetailsViewModel>(
    FragmentPhotoDetailsBinding::inflate
) {
    override val viewModel by viewModels<PhotoDetailsViewModel>()

   private var data : Hit?= null
    private var stringComment : String = "yorum"

    override fun onCreateFinished() {
        data = arguments?.getSerializable(PHOTO_DATA) as Hit
    }
    override fun initializeListeners() {
        data?.webformatURL?.let { binding.imageDetail.loadImage(it) }
        binding.likeDetailsTv.text = data?.likes.toString()
        binding.commentDetailsTv.text = "(${data?.comments} $stringComment)"
        data?.userImageURL?.let { binding.userImage.loadImage(it) }
        binding.userName.text = data?.user
    }
    override fun observeEvents() {
       //TODO : Observe olan bir datamız olmadığı için burası boş
    }

    override fun initAdapters(data: List<Hit>) {
        //TODO: Adapter'a ihtiyacımız olmadığından burası boş.
    }


}
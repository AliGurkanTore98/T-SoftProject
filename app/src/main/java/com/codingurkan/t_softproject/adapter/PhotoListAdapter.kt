package com.codingurkan.t_softproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingurkan.t_softproject.databinding.PhotoListItemBinding
import com.codingurkan.t_softproject.model.Hit
import com.codingurkan.t_softproject.util.loadImage

class PhotoListAdapter(private val list : List<Hit>,
      private val itemClickListener : ItemClickListener) : RecyclerView.Adapter<PhotoListAdapter.PhotoListVH>() {

    class PhotoListVH(val binding : PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root)


     interface ItemClickListener{
         fun onClick(data : Hit)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListVH {
        val view = PhotoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoListVH(view)
    }
    override fun onBindViewHolder(holder: PhotoListVH, position: Int) {
        with(holder.binding){
            //imageViewList.layoutParams.width = list[position].previewWidth
            //imageViewList.layoutParams.height = list[position].previewHeight

            imageViewList.loadImage(list[position].previewURL)
            likeTv.text = list[position].likes.toString()
            commentTv.text = "(${list[position].comments} yorum)"
            viewsTv.text = "${list[position].views/(1000)}K(görüntülenme)"

            imageViewList.setOnClickListener {
                itemClickListener.onClick(list[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
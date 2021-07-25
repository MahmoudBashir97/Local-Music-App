package com.mahmoudbashir.musician_app.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.musician_app.databinding.SongItemBinding
import com.mahmoudbashir.musician_app.fragments.ListMusicFragmentDirections
import com.mahmoudbashir.musician_app.models.Song
import java.nio.channels.AsynchronousCloseException

class SongsAdapter:RecyclerView.Adapter<SongsAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: SongItemBinding):
            RecyclerView.ViewHolder(binding.root)

    private val differCallback = object :DiffUtil.ItemCallback<Song>(){
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.songUri == newItem.songUri
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(SongItemBinding.inflate(
           LayoutInflater.from(parent.context),parent,false
       ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currsong = differ.currentList[position]

        holder.binding.apply {
            tvDuration.text = currsong.songDuration
            songTitle.text = currsong.songTitle
            songArtist.text = currsong.songArtist
            tvOrder.text = "${position + 1}"
        }
        holder.itemView.setOnClickListener {mView ->
            val direction =  ListMusicFragmentDirections.actionListMusicFragmentToMusicPlayFragment(currsong)
            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}
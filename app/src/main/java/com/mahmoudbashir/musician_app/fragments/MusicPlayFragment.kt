package com.mahmoudbashir.musician_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mahmoudbashir.musician_app.R
import com.mahmoudbashir.musician_app.databinding.FragmentMusicPlayBinding
import com.mahmoudbashir.musician_app.models.Song


class MusicPlayFragment : Fragment(R.layout.fragment_music_play) {
    private  var _binding:FragmentMusicPlayBinding?=null
    private val binding get() = _binding!!
    private val args : MusicPlayFragmentArgs by navArgs()
    private lateinit var song:Song

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentMusicPlayBinding.inflate(
           inflater,
           container,
           false
       )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        song = args.song!!
        binding.tvAuthor.text = song.songArtist
        binding.tvTitle.text = song.songTitle
        binding.tvDuration.text = song.songDuration


    }

}
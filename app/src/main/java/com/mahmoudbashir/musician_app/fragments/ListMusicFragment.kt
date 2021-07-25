package com.mahmoudbashir.musician_app.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudbashir.musician_app.R
import com.mahmoudbashir.musician_app.adapters.SongsAdapter
import com.mahmoudbashir.musician_app.databinding.FragmentListMusicBinding
import com.mahmoudbashir.musician_app.helper.Constants
import com.mahmoudbashir.musician_app.helper.Constants.toast
import com.mahmoudbashir.musician_app.models.Song

class ListMusicFragment : Fragment(R.layout.fragment_list_music) {
    private  var _binding:FragmentListMusicBinding?=null
    private val binding get() = _binding!!
    private val songList:MutableList<Song> = ArrayList()
    private lateinit var songsAd: SongsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMusicBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setUpRecyclerView(){
        songsAd = SongsAdapter()
        binding.rvSongList.apply {
            adapter = songsAd
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        songsAd.differ.submitList(songList)
        songList.clear()
    }

    //load songs
    private fun loadSongs(){
        val allSongUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC +"!=0"
        val sortOrder = " ${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val cursor = activity?.applicationContext?.contentResolver!!.query(
            allSongUri, null, selection, null, sortOrder
        )
        if (cursor != null){
            while (cursor.moveToNext()){
                val songUri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                var songDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))


                if (songDuration == null){
                    songDuration="00"
                    Log.d("duratTest : ","$songDuration")
                }

                //let's convert the song duration
                val songDurationLong =songDuration.toLong()
                songList.add(
                    Song(
                        songTitle, songAuthor, songUri,
                        Constants.durationConverter(songDurationLong)
                    )
                )
            }
            cursor.close()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSongs()
        setUpRecyclerView()
        checkUserPermission()
    }

    //check permission
    private fun checkUserPermission(){
        if (activity?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            } != PackageManager.PERMISSION_GRANTED
        ){
            requestPermissions(
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.REQUEST_CODE_FOR_PERMISSIONS
            )
            return
        }
       // activity?.toast("Granted")
        loadSongs()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            Constants.REQUEST_CODE_FOR_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activity?.toast("Permission Granted")
                loadSongs()
            } else {
                activity?.toast("Permission Denied")
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
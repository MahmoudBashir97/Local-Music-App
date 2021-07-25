package com.mahmoudbashir.musician_app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val songTitle:String?,
    val songArtist:String?,
    val songUri:String?,
    val songDuration:String?
):Parcelable

package org.wit.recipesapp.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(
    var uid: String? = "n/a",
                       var title: String = "",
                       var description: String = "",
                   //    var image: Uri = Uri.EMPTY,
                       var lat : Double = 0.0,
                       var lng: Double = 0.0,
                       var zoom: Float = 0f,
                       val email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
         //   "image" to image,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom,
            "email" to email
        )
    }
}


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

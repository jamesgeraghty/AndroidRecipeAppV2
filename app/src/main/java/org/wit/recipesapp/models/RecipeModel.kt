package org.wit.recipesapp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class RecipeModel(
    var uid: String? = "n/a",
    var title: String = "",
    ///private var meals: Array<String> = arrayOf(""),
    var description: String = "",
                   //    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
       //     "meals" to meals,
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

package org.wit.recipesapp.models

import android.os.Parcelable
import android.util.ArrayMap
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class RecipeModel(
    var uid: String? = "",
    var title: String = "",
    //private var meals: ArrayMap<String> = arrayOf(""),
    var description: String = "",
                   //    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var profilepic: String = "",
    var email: String? = "") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
          //  "meals" to meals,
            "description" to description,
         //   "image" to image,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

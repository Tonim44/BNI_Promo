package id.co.tonim.bniapppromo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PromoItem(val name: String, val date : String, val desc: String, val imageUrl: String) :
    Parcelable


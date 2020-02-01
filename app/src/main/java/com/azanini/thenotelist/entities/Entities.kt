package com.azanini.thenotelist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Notebook(val title: String?, val description: String?, val image: String?) : Parcelable



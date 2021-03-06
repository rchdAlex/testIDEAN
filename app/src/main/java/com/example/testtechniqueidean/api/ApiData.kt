package com.example.testtechniqueidean.api

import android.os.Parcel
import android.os.Parcelable
import com.example.testtechniqueidean.R

/*
class retrieves data from api

liked var for like and unlike movie
 */
data class ApiData(
    val title: String?,
    val description: String?,
    val director: String?,
    val producer: String?,
    val release_date: Int,
    val people: List<String>?,
    val url: String?,
    var liked: Boolean,
    var image: Int ,
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(director)
        parcel.writeString(producer)
        parcel.writeInt(release_date)
        parcel.writeStringList(people)
        parcel.writeString(url)
        parcel.writeByte(if (liked) 1 else 0)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiData> {
        override fun createFromParcel(parcel: Parcel): ApiData {
            return ApiData(parcel)
        }

        override fun newArray(size: Int): Array<ApiData?> {
            return arrayOfNulls(size)
        }
    }

}
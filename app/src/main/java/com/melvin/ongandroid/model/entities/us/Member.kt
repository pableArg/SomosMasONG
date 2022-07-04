package com.melvin.ongandroid.model.entities.us

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member (
    val id: Int,
    val name: String?,
    val image: String?,
    val description: String?,
    @SerializedName("facebookUrl")
    val facebookURL: String?,
    @SerializedName("linkedinUrl")
    val linkedinURL: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?
): Parcelable



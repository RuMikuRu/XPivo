package com.example.xpivo.data.request

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("middleName")
    val middleName: String? = null,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("photo")
    val photo: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserUpdateRequest

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (middleName != other.middleName) return false
        if (birthDate != other.birthDate) return false
        if (email != other.email) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (!photo.contentEquals(other.photo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + (middleName?.hashCode() ?: 0)
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (photo?.contentHashCode() ?: 0)
        return result
    }
}

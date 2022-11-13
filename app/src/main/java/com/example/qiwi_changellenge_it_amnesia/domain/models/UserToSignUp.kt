package com.example.qiwi_changellenge_it_amnesia.domain.models

data class UserToSignUp (
    val login: String,
    val password: String?,
    val phoneNumber: String,
)
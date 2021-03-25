package com.example.testtechniqueidean.api

data class ApiData(
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val release_date: Int,
    val people: List<String>,
    val url: String,
    var liked: Boolean
) {
}
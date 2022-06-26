package com.codingurkan.t_softproject.model

data class PhotoResponseModel(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)
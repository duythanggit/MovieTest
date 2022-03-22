package com.duythangjr.test.model

data class PopularResponse(
    val page: Int,
    val results: ArrayList<ResultPopular>,
    val total_pages: Int,
    val total_results: Int,
)


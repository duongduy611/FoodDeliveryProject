package com.example.foodappprm.model

data class Product(
    val product_id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val category_id: String = "",
    val rating: Float = 0.0f,
    val isAvailable: Boolean = true,
    val description_short: String = "",
    val description_long: String = "",
    val ingredients: List<String> = listOf(),
    val nutrition: Map<String, String> = mapOf()
) {
    constructor() : this("", "", 0.0, "", "", 0.0f, true, "", "", listOf(), mapOf())
}

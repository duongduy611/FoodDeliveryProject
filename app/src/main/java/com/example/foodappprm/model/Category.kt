package com.example.foodappprm.model

data class Category(
    val category_id: String = "",
    val name: String = "",
    val iconName: String = "" // Tên file drawable, ví dụ: "fried_potatoes", "hamburger", etc.
) {
    constructor() : this("", "", "")
}

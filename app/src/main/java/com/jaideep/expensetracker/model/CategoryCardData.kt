package com.jaideep.expensetracker.model

class CategoryCardData {
    var categoryName: String = ""
    var iconName: String = ""
    var amountSpent: Double = 0.0

    constructor()
    constructor(categoryName: String, iconName: String, amountSpent: Double) {
        this.categoryName = categoryName
        this.iconName = iconName
        this.amountSpent = amountSpent
    }

}
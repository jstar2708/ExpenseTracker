package com.jaideep.expensetracker.common

object MainScreen {
    const val HOME = "HOME"
    const val TRANSACTIONS = "TRANSACTIONS"
    const val CATEGORY = "CATEGORY"
    const val SETTINGS = "SETTINGS"
}

object AddScreen {
    const val ADD_SCREEN = "add_screen"
    const val ADD_TRANSACTION = "ADD_TRANSACTIONS"
    const val ADD_ACCOUNT = "ADD_ACCOUNT"
    const val ADD_CATEGORY = "ADD_CATEGORY"
}

object DetailScreen {
    const val CATEGORY_DETAILS = "category_details"
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
    const val DETAIL = "detail_graph"
}

sealed class AuthGraph(val route: String) {
    data object LOGIN : AuthGraph("login")
    data object REGISTER : AuthGraph("register")
    data object SPLASH : AuthGraph("splash")
}

package com.jaideep.expensetracker.common

object MainScreen {
    const val HOME = "home"
    const val TRANSACTION = "transaction"
    const val CATEGORY = "category"
}

object AddScreen {
    const val CREATE_UPDATE_TRANSACTION = "cu_transaction"
    const val CREATE_UPDATE_TRANSACTION_ROUTE = "cu_transaction/{id}"
    const val CREATE_UPDATE_ACCOUNT = "cu_account"
    const val CREATE_UPDATE_ACCOUNT_ROUTE = "cu_account/{id}"
    const val CREATE_UPDATE_CATEGORY = "cu_category"
    const val CREATE_UPDATE_CATEGORY_ROUTE = "cu_category/{id}"
}

object DetailScreen {
    const val NOTIFICATION = "notification"
    const val CATEGORY_DETAILS = "category_details"
    const val CATEGORY_DETAILS_ROUTE = "category_details/{categoryName}"
    const val PROFILE = "profile"
}

object AuthScreen {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val SPLASH = "splash"
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
    const val DETAIL = "detail_graph"
}

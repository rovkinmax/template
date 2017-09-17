package ru.rovkinmax.template.view

interface ErrorView {
    fun showErrorMessage(message: String, needCallback: Boolean = false)

    fun showNetworkError()

    fun showUnexpectedError()

    fun logout()
}
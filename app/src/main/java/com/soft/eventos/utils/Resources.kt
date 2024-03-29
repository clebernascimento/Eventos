package com.soft.eventos.utils

data class Resources<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T): Resources<T> =
            Resources(status = Status.LOADING, data = data, message = null)

        fun <T> success(data: T): Resources<T> =
            Resources(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resources<T> =
            Resources(status = Status.ERROR, data = data, message = message)
    }
}
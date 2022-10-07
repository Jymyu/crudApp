package com.example.crudapp.network

class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val responseCode: Int?
) {
    companion object {
        fun <T> success(data: T?, responseCode: Int): Resource<T> = Resource(
            status = Status.SUCCESS,
            data = data,
            message = null,
            responseCode = responseCode
        )

        fun <T> loading(data: T): Resource<T> = Resource(
            status = Status.LOADING,
            data = null,
            message = null,
            null
        )

        fun <T> error(message: String?, responseCode: Int): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = null,
                message = message,
                responseCode = responseCode
            )
    }
}
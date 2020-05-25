package com.example.base

sealed class Error {

    class NetworkError() : Error()
    class EmptyBodyError(): Error()
    class RandomError(val cause: Throwable?): Error()
}

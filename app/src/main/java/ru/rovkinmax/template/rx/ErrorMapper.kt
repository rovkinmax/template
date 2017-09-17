package ru.rovkinmax.template.rx

import retrofit2.HttpException

interface ErrorMapper {
    fun provideMessage(httpException: HttpException): String
}

class DefaultMapper : ErrorMapper {
    override fun provideMessage(httpException: HttpException): String = httpException.message()
}
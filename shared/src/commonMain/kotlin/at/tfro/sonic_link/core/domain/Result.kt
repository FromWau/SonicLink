package at.tfro.sonic_link.core.domain

import at.tfro.sonic_link.core.domain.Error as DomainError

sealed interface Result<out T, out E : DomainError> {
    data class Success<out T>(val data: T) : Result<T, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : DomainError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> = when (this) {
    is Result.Error -> Result.Error(error)
    is Result.Success -> Result.Success(map(data))
}

fun <T, E : DomainError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> = map { }

inline fun <T, E : DomainError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : DomainError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> =
    when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }

typealias EmptyResult<E> = Result<Unit, E>

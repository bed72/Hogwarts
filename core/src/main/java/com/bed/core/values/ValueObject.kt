package com.bed.core.values

import arrow.core.nel
import arrow.core.left
import arrow.core.Either
import arrow.core.Tuple4
import arrow.core.EitherNel
import arrow.core.NonEmptyList
import arrow.core.raise.either

fun NonEmptyList<MessageValue>.getFirstMessage() = this.toList().first().message

fun <A, B> Either<A, B>.leftNel(): Either<NonEmptyList<A>, B> = mapLeft { it.nel() }

fun <E, A, R> Either<E, A>.andThen(f: (A) -> Either<E, R>): Either<E, R> = fold({ it.left() }, f)

fun <E, A, R> validate(
    a: EitherNel<E, A>,
    f: (A) -> R
): EitherNel<E, R> = either { f(a.bind()) }

fun <E, A, B, R> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>,
    f: (A, B) -> R
): EitherNel<E, R> = either { f(a.bind(), b.bind()) }

fun <E, A, B> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>
): EitherNel<E, Pair<A, B>> = either { a.bind() to b.bind() }

fun <E, A, B, C, R> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>,
    c: EitherNel<E, C>,
    f: (A, B, C) -> R
): EitherNel<E, R> = either { f(a.bind(), b.bind(), c.bind()) }

fun <E, A, B, C> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>,
    c: EitherNel<E, C>
): EitherNel<E, Triple<A, B, C>> = either { Triple(a.bind(), b.bind(), c.bind()) }

fun <E, A, B, C, D> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>,
    c: EitherNel<E, C>,
    d: EitherNel<E, D>
): EitherNel<E, Tuple4<A, B, C, D>> = either { Tuple4(a.bind(), b.bind(), c.bind(), d.bind()) }

fun <E, A, B, C, D, R> validate(
    a: EitherNel<E, A>,
    b: EitherNel<E, B>,
    c: EitherNel<E, C>,
    d: EitherNel<E, D>,
    f: (A, B, C, D) -> R
): EitherNel<E, R> = either { f(a.bind(), b.bind(), c.bind(), d.bind()) }

sealed class ApplicationMessageValue(open val message: String?) {
    override fun toString(): String = message ?: "null"
}

data class MessageValue(override val message: String) : ApplicationMessageValue(message)

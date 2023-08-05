package com.bed.seller.mappers

interface Mapper<in P, out R> {
    operator fun invoke(parameter: P): R
}

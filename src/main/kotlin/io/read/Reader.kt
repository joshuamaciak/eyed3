package io.read

import java.io.InputStream

interface Reader<T> {
    fun read(stream: InputStream): T
}
package io.read

import model.tag.Tag
import java.io.InputStream

abstract class TagReader {
    abstract fun read(stream: InputStream): Tag
}
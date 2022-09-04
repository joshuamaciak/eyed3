package model

class Flags(byte: Byte) {
    private val value = byte.toInt()

    fun isSet(bitIndex: Int): Boolean {
        val mask = 1 shl((bitIndex-1).coerceAtLeast(0))
        return value and mask != 0
    }
}
package org.ktoggl

open class EnumCompanion<in T, out V>(private val valueMap: Map<T, V>) {

    fun fromValue(type: T) = valueMap[type] ?: throw IllegalArgumentException()
}

internal fun String.fromHexColorToInt(): Int {

    val colorStr = if (this.startsWith("#")) this.substring(1) else this
    var color = colorStr.toLongOrNull(16) ?: 0

    if (colorStr.length == 6) {
        color = color or 0x00000000ff000000
    }
    return color.toInt()
}

internal fun String?.defaultIfBlank(default: String) = if (this.isNullOrBlank()) default else this!!
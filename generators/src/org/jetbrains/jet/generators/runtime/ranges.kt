/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.generators.runtime.ranges

import org.jetbrains.jet.generators.runtime.*
import org.jetbrains.jet.generators.runtime.ranges.RangeKind.*
import java.io.PrintWriter

enum class RangeKind {
    BYTE
    CHAR
    SHORT
    INT
    LONG
    FLOAT
    DOUBLE

    val capitalized: String get() = name().toLowerCase().capitalize()
}

class GenerateRanges(val out: PrintWriter) {
    fun generate() {
        generatedBy(out, javaClass.getName())
        for (kind in RangeKind.values()) {
            val t = kind.capitalized
            val range = "${t}Range"

            val incrementType = when (kind) {
                BYTE, CHAR, SHORT -> "Int"
                else -> kind.capitalized
            }

            val increment = when (kind) {
                FLOAT -> "1.0f"
                DOUBLE -> "1.0"
                else -> "1"
            }

            val emptyBounds = when (kind) {
                CHAR -> "1.toChar(), 0.toChar()"
                FLOAT -> "1.0f, 0.0f"
                DOUBLE -> "1.0, 0.0"
                else -> "1, 0"
            }

            val toString = "\$start..\$end"

            fun compare(v: String) = when (kind) {
                FLOAT, DOUBLE -> "java.lang.${kind.capitalized}.compare($v, that.$v) == 0"
                else -> "$v == that.$v"
            }

            val hashCode = when (kind) {
                BYTE, CHAR, SHORT -> " = 31 * start.toInt() + end"
                INT -> " = 31 * start + end"
                LONG -> " = (31 * (start xor (start ushr 32)) + (end xor (end ushr 32))).toInt()"
                FLOAT -> " = (31 * (if (start != +0.0.toFloat()) java.lang.Float.floatToIntBits(start) else 0) +\n" +
                "        (if (end != +0.0.toFloat()) java.lang.Float.floatToIntBits(end) else 0)).toInt()"
                DOUBLE -> ": Int {\n" +
                "        var temp = if (start != +0.0) java.lang.Double.doubleToLongBits(start) else 0\n" +
                "        val result = (temp xor (temp ushr 32)).toInt()\n" +
                "        temp = if (end != +0.0) java.lang.Double.doubleToLongBits(end) else 0\n" +
                "        return 31 * result + (temp xor (temp ushr 32)).toInt()\n" +
                "    }"
            }

            out.println(
"""public class $range(public override val start: $t, public override val end: $t) : Range<$t>, Progression<$t> {
    override val increment: $incrementType
        get() = $increment

    override fun contains(item: $t) = start <= item && item <= end

    override fun iterator() = ${t}ProgressionIterator(start, end, $increment)

    fun equals(o: Any?): Boolean {
        val that = o as? $range ?: return false
        return ${compare("start")} && ${compare("end")}
    }

    fun hashCode()$hashCode

    fun toString() = "$toString"

    class object {
        public val EMPTY: $range = $range($emptyBounds)
    }
}""")
            out.println()
        }
    }
}

fun main(args: Array<String>) {
    generateRuntimeFile("Ranges.kt") {
        GenerateRanges(it).generate()
    }
}

/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

package org.jetbrains.jet.generators.runtime.progressionIterators

import org.jetbrains.jet.generators.runtime.*
import org.jetbrains.jet.generators.runtime.ProgressionKind.*
import java.io.PrintWriter

fun integerProgressionIterator(kind: ProgressionKind): String {
    val t = kind.capitalized

    val incrementType = progressionIncrementType(kind)

    val (toInt, toType) = when (kind) {
        BYTE, CHAR, SHORT -> ".toInt()" to ".to$t()"
        else -> "" to ""
    }

    return """class ${t}ProgressionIterator(start: $t, end: $t, val increment: $incrementType) : ${t}Iterator() {
    private var next = start$toInt
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start$toInt, end$toInt, increment)$toType
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun next$t(): $t {
        val value = next
        if (value == finalElement$toInt) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value$toType
    }
}"""
}

fun floatingPointProgressionIterator(kind: ProgressionKind): String {
    val t = kind.capitalized

    return """class ${t}ProgressionIterator(start: $t, val end: $t, val increment: $t) : ${t}Iterator() {
    private var next = start

    override fun hasNext() = if (increment > 0) next <= end else next >= end

    override fun next$t(): $t {
        val value = next
        next += increment
        return value
    }
}"""
}

class GenerateProgressionIterators(val out: PrintWriter) {
    fun generate() {
        generatedBy(out, javaClass.getName())
        out.println("import jet.runtime.ProgressionUtil")
        out.println()
        for (kind in ProgressionKind.values()) {
            if (kind != FLOAT && kind != DOUBLE) {
                out.println(integerProgressionIterator(kind))
            }
            else {
                out.println(floatingPointProgressionIterator(kind))
            }
            out.println()
        }
    }
}

fun main(args: Array<String>) {
    generateBuiltInFile("ProgressionIterators.kt") {
        GenerateProgressionIterators(it).generate()
    }
}

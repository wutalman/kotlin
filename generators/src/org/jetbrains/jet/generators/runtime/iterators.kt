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

package org.jetbrains.jet.generators.runtime.iterators

import org.jetbrains.jet.generators.runtime.*
import java.io.PrintWriter

enum class IteratorKind {
    BYTE
    CHAR
    SHORT
    INT
    LONG
    FLOAT
    DOUBLE
    BOOLEAN

    val capitalized: String get() = name().toLowerCase().capitalize()
}

class GenerateIterators(val out: PrintWriter) {
    fun generate() {
        generatedBy(out, javaClass.getName())
        for (kind in IteratorKind.values()) {
            val s = kind.capitalized
            out.println("public abstract class ${s}Iterator : Iterator<$s> {")
            out.println("    override final fun next() = next$s()")
            out.println()
            out.println("    public abstract fun next$s(): $s")
            out.println("}")
            out.println()
        }
    }
}

fun main(args: Array<String>) {
    generateBuiltInFile("Iterators.kt") {
        GenerateIterators(it).generate()
    }
}

package a

import a.E.ENTRY

<selection> trait T

class A(i: Int) {}

val c = 0

fun g(a: A) {}

fun A.ext()

object O1 {
    fun f() {
    }
}

object O2 {
}

enum class E {
    ENTRY
}

fun f(a: A, t: T) = {
    g(A(c).ext())
    O1.f()
    O2
    ENTRY
}</selection>
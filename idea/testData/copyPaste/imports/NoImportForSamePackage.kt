package a

trait T

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

<selection>fun f(a: A, t: T) = {
    g(A(c).ext())
    O1.f()
    O2
}</selection>
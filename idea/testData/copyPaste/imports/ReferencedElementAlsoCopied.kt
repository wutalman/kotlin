package a <selection>
trait T

class A(i: Int) {}

val c = 0

fun g(a: A) {}

fun A.ext()

fun f(a: A, t: T) = g(A(c).ext())</selection>
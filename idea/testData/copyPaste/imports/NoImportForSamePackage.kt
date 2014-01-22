package a

trait T

class A {}

fun g(a: A) {}

fun A.ext()

<selection>fun f(a: A, t: T) = g(A().ext())</selection>
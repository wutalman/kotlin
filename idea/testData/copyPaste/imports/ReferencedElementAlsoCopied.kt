package a

<selection>
trait T

class A: T

fun T.ext() {}

fun f(a: A) { a.ext() }
</selection>
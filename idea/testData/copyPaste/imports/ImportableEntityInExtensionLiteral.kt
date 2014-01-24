package a

import a.A.Inner
import a.A.Nested

class A {
    class Inner {
    }
    class Nested {
    }
    fun f() {
    }
}

fun A.ext() {
}

fun f(body: A.() -> Unit) {
}

<selection>fun g() {
    f {
        Inner()
        Nested()
        f()
        ext()
    }
}</selection>
package to

import a.f
import a.A.Inner
import a.A.Nested
import a.ext

fun g() {
    f {
        Inner()
        Nested()
        f()
        ext()
    }
}
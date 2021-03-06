class Foo {}

fun Foo.invoke() {}

//no variable
fun test(foo: Foo) {
    foo()
}

//variable as member
trait A {
    val foo: Foo
}


fun test(a: A) {
    a.foo()

    with (a) {
        foo()
    }
}

//variable as extension
trait B {
}
val B.foo = Foo()

fun test(b: B) {
    b.foo()

    with (b) {
        foo()
    }
}

//variable as member extension
trait C

trait D {
    val C.foo: Foo

    fun test(c: C) {
        c.foo()

        with (c) {
            foo()
        }
    }
}

fun test(d: D, c: C) {
    with (d) {
        c.foo()

        with (c) {
            foo()
        }
    }
}

//--------------
fun <T, R> with(receiver: T, f: T.() -> R) : R = receiver.f()
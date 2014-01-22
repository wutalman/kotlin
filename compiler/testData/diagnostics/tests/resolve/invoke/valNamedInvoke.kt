trait A

fun foo(invoke: A.()->Unit, a: A) {
    a.invoke()
}

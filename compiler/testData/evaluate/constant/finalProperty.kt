package test

// val prop1: 1.toInt()
val prop1 = A().a

// val prop2: 2.toInt()
val prop2 = A().a + 1

class A() {
    val a = 1

    // val prop3: 1.toInt()
    val prop3 = a

    // val prop4: 2.toInt()
    val prop4 = a + 1

    fun foo() {
        // val prop5: 1.toInt()
        val prop5 = A().a

        // val prop6: 2.toInt()
        val prop6 = A().a + 1
    }
}

fun foo() {
    // val prop7: 1.toInt()
    val prop7 = A().a

    // val prop8: 2.toInt()
    val prop8 = A().a + 1
}
package kotlin

annotation class Ann(vararg val i: Int)

Ann(<!ANNOTATION_PARAMETER_MUST_BE_CONST!>i<!>)
Ann(i2)
Ann(<!ANNOTATION_PARAMETER_MUST_BE_CONST!>i3<!>)
Ann(<!ANNOTATION_PARAMETER_MUST_BE_CONST!>i<!>, i2, <!ANNOTATION_PARAMETER_MUST_BE_CONST!>i3<!>)
Ann(*<!ANNOTATION_PARAMETER_MUST_BE_CONST!>intArray(i)<!>)
Ann(*intArray(i2))
Ann(*<!ANNOTATION_PARAMETER_MUST_BE_CONST!>intArray(i3)<!>)
Ann(*<!ANNOTATION_PARAMETER_MUST_BE_CONST, ANNOTATION_PARAMETER_MUST_BE_CONST!>intArray(i, i2, i3)<!>)
class Test

var i = 1
val i2 = 1
val i3 = foo()

fun foo(): Int = 1

fun intArray(vararg content : Int) : IntArray = content
package a

import a.Outer.*

class Outer {
    inner class Inner {
    }
    inner class Inner2 {
    }
    class Nested {
    }
    enum class NestedEnum {
    }
    object NestedObj {
    }
    trait NestedTrait {
    }
    annotation class NestedAnnotation
}

<selection>fun f(i: Inner, n: Nested, e: NestedEnum, o: NestedObj, t: NestedTrait, a: NestedAnnotation) {
    Outer().Inner2()
}</selection>
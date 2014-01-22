package a.b.c

class A() {}

class B(): B() {}

<selection> fun f(a: a.b.c.A): a.b.c.B = a.b.c.B()</selection>
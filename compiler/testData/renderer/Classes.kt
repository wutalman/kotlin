package rendererTest

annotation class TheAnnotation

[TheAnnotation]
public open class TheClass<out T : Int, X> {
  private val privateVal: Int = 5

  val shouldBeFinal: Int = 5

  protected abstract fun foo() {}

  private class Inner {}
}

internal class InternalClass {
}

trait TheTrait {
  fun abstractFun()

  class object : TheClass<Int, Int> {
    fun classObjectFunction(): Int {}
  }
}

public class WithReified<reified A, reified out B>

public trait TwoUpperBounds<T> where T : Number, T : Any

//package rendererTest
//internal final annotation class TheAnnotation : kotlin.Annotation defined in rendererTest
//public constructor TheAnnotation() defined in rendererTest.TheAnnotation
//rendererTest.TheAnnotation public open class TheClass<out T : kotlin.Int, X> defined in rendererTest
//public constructor TheClass<out T : kotlin.Int, X>() defined in rendererTest.TheClass
//<out T : kotlin.Int> defined in rendererTest.TheClass
//<X> defined in rendererTest.TheClass
//private final val privateVal: kotlin.Int defined in rendererTest.TheClass
//internal final val shouldBeFinal: kotlin.Int defined in rendererTest.TheClass
//protected abstract fun foo(): kotlin.Unit defined in rendererTest.TheClass
//private final class Inner defined in rendererTest.TheClass
//public constructor Inner() defined in rendererTest.TheClass.Inner
//internal final class InternalClass defined in rendererTest
//public constructor InternalClass() defined in rendererTest.InternalClass
//internal trait TheTrait defined in rendererTest
//internal abstract fun abstractFun(): kotlin.Unit defined in rendererTest.TheTrait
//internal class object : rendererTest.TheClass<kotlin.Int, kotlin.Int> defined in rendererTest.TheTrait
//private constructor <class-object-for-TheTrait>() defined in rendererTest.TheTrait.<class-object-for-TheTrait>
//internal final fun classObjectFunction(): kotlin.Int defined in rendererTest.TheTrait.<class-object-for-TheTrait>
//public final class WithReified<reified A, reified out B> defined in rendererTest
//public constructor WithReified<reified A, reified out B>() defined in rendererTest.WithReified
//<reified A> defined in rendererTest.WithReified
//<reified out B> defined in rendererTest.WithReified
//public trait TwoUpperBounds<T : kotlin.Number> where T : kotlin.Any defined in rendererTest
//<T : kotlin.Number & kotlin.Any> defined in rendererTest.TwoUpperBounds

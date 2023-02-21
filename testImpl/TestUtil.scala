import org.scalatest.freespec.AnyFreeSpec

object TestUtil extends AnyFreeSpec:

    def implementTest(test: Object, shouldReturn: Object): Unit =
        test.getClass.getSimpleName - (
            shouldReturn.getClass.getSimpleName - (
                shouldReturn.getClass
                    .getMethods
                    .toList
                    .tail
                    .foreach(method =>
                        method.getName in method
                    )
            )    
        )
import org.scalatest.freespec.AnyFreeSpec
import java.lang.reflect.{InvocationTargetException, Method}
import scala.util.{Failure, Success, Try}

class TestUtil extends AnyFreeSpec:

    def implementTest(test: Object, shouldReturn: Object): Unit =
        test.getClass.getSimpleName - (
            shouldReturn.getClass.getSimpleName -
                shouldReturn.getClass
                    .getMethods
                    .toList
                    .filter(_.getDeclaringClass == shouldReturn.getClass)
                    .foreach(testMethod =>
                        testMethod.getName in(
                            Try(testMethod.invoke(shouldReturn)) match
                                case Failure(exception) => throw exception.getCause
                                case Success(value) => value
                        )
                    )
        )
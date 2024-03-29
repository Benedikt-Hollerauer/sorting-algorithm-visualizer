import core.model.SortingModel
import core.typeClass.{GetBarModel, GetBarVisualisation}
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success, Try}

class TestUtil extends AnyFreeSpec:

    def implementTest(test: Object): Unit =
        test.getClass.getSimpleName -
            test.getClass
                .getDeclaredFields
                .map:
                    _.getType
                .filterNot:
                    _ == test.getClass
                .foreach: shouldReturn =>
                    shouldReturn.getSimpleName -
                        shouldReturn.getMethods
                            .filter:
                                _.getDeclaringClass == shouldReturn
                            .foreach: testMethod =>
                                testMethod.getName in(
                                    Try(testMethod.invoke(shouldReturn.getDeclaredConstructor().newInstance())) match
                                        case Failure(exception) => throw exception.getCause
                                        case Success(value) => value
                                )
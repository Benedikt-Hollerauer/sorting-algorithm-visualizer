import org.scalatest.freespec.AnyFreeSpec

import java.lang.reflect.Method

class TestUtil extends AnyFreeSpec:

    def implementTest(test: Object, shouldReturn: Object): Unit =
        test.getClass.getSimpleName - (
            shouldReturn.getClass.getSimpleName - {
                val listOfMethods: List[Method] = shouldReturn.getClass
                    .getDeclaredMethods
                    .toList
                    .filterNot(_.getName == "writeReplace")
                    .filterNot(_.getName == "$deserializeLambda$")

                listOfMethods.foreach(x => println(x.getName))

                listOfMethods.foreach(testMethod =>
                    testMethod.getName in testMethod.invoke
                )
            }
        )
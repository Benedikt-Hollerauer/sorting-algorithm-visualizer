import TestUtil.*
import coreTest.useCaseTest.SortByBubbleSortUseCase_Test
import coreTest.useCaseTest.SortByBubbleSortUseCase_Test.apply_should_return
import org.scalatest.freespec.AnyFreeSpec

class UseCase_TestImpl extends AnyFreeSpec:

    implementTest(SortByBubbleSortUseCase_Test, apply_should_return)
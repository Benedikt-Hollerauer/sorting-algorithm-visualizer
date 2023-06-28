import test.useCaseTest.{GenerateSortableUseCase_Test, VisualizeSortingUseCase_Test}
import useCaseTest.SortByBubbleSortUseCase_Test

class UseCase_TestImpl extends TestUtil:

    implementTest(SortByBubbleSortUseCase_Test)
    implementTest(GenerateSortableUseCase_Test)
    implementTest(VisualizeSortingUseCase_Test)
import core.model.SortingModel
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}
import test.useCaseTest.{GenerateSortableUseCase_Test, SortByInsertionSortUseCase_Test, VisualizeSortingUseCase_Test}
import useCaseTest.SortByBubbleSortUseCase_Test

class UseCase_TestImpl extends TestUtil:

    Set(
        SortByBubbleSortUseCase_Test,
        GenerateSortableUseCase_Test,
        VisualizeSortingUseCase_Test,
        SortByInsertionSortUseCase_Test
    ).foreach(implementTest)
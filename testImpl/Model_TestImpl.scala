import core.model.SortingModel
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}
import test.modelTest.*

class Model_TestImpl extends TestUtil:

    def testModels(
        using getBarVisualisation: GetBarVisualisation[SortingModel]
    )(
        using getBarModel: GetBarModel[SortingModel]
    ): Unit =
        Set(
            SortableModel_Test,
            IndexModel_Test
        ).foreach(implementTest)
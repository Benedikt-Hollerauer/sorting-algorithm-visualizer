import core.model.SortingModel
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}
import test.entityTest.*

class Entity_TestImpl extends TestUtil:

	Set(
		BubbleSortEntity_Test,
		VisualizeEntity_Test,
		VisualizeEntity_BubbleSort_Test,
		VisualizeEntity_InsertionSort_Test,
		InsertionSortEntity_Test
	).foreach(implementTest)
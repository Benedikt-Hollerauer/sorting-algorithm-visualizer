import core.model.SortingModel
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}
import test.Util_Test

class Util_TestImpl extends TestUtil:

	def testUtils(
		using getBarVisualisation: GetBarVisualisation[SortingModel]
	)(
		using getBarModel: GetBarModel[SortingModel]
	): Unit =
		Set(
			Util_Test
		).foreach(implementTest)
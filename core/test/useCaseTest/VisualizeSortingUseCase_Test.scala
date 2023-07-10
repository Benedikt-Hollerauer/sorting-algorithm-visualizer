package test.useCaseTest

import core.model.{BarStateModel, BarModel, NonEmptyListModel, SortableModel}
import core.useCase.VisualizeSortingUseCase
import mock.ToBeSortedMock
import mock.inputMock.VisualizeSortingInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil.assertRight

object VisualizeSortingUseCase_Test:

	object apply_should_return:

		def `LazyList[NonEmptyListModel[BarModel]]`: Unit =
			val res = Right(
				VisualizeSortingUseCase(
					input = VisualizeSortingInputMock.input
				)
			)
			assertRight(res)(
				(res: LazyList[NonEmptyListModel[BarModel]]) =>
					val headList = res.head.list
					val lastList = res.last.list
					Seq(
						res.head.list.head.value == 636,
						res.head.list.last.value == 259,
						res.last.list.head.value == ToBeSortedMock.smallest,
						res.last.list.last.value == ToBeSortedMock.biggest,
						res.head.list.head.barColor == BarStateModel.Swapped,
						res.head.list.last.barColor == BarStateModel.Normal,
						res.last.list.head.barColor == BarStateModel.Swapped,
						res.last.list.last.barColor == BarStateModel.Normal,
						headList.length == lastList.length
					)
			)
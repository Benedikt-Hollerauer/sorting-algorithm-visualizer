package test.useCaseTest

import core.model.{BarModel, BarStateModel, NonEmptyListModel, SortableModel, VisualizeModel}
import core.useCase.VisualizeSortingUseCase
import mock.ToBeSortedMock
import mock.inputMock.VisualizeSortingInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil.assertRight

object VisualizeSortingUseCase_Test:

	object apply_should_return:

		def `VisualizeModel`: Unit =
			val res = Right(
				VisualizeSortingUseCase(
					input = VisualizeSortingInputMock.input
				)
			)
			assertRight(res)(
				(res: VisualizeModel) =>
					Seq(
						res.changes.head.list.head.value == ToBeSortedMock.unsorted.head, //TODO this is the same as in VisualizeEntity_Test
						res.changes.head.list.last.value == ToBeSortedMock.unsorted.last,
						res.changes.last.list.head.value == ToBeSortedMock.smallest,
						res.changes.last.list.last.value == ToBeSortedMock.biggest,
						res.changes.head.list.head.barState == BarStateModel.Swapped,
						res.changes.head.list.last.barState == BarStateModel.Normal,
						res.changes.last.list.head.barState == BarStateModel.Swapped,
						res.changes.last.list.last.barState == BarStateModel.AlreadySorted,
						res.changes.head.list.length == res.changes.head.list.length,
						res.finishedSorting.list.head.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.last.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.head.value == ToBeSortedMock.smallest,
						res.finishedSorting.list.last.value == ToBeSortedMock.biggest
					)
			)
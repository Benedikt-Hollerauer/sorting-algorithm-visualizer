package test.useCaseTest

import core.model.*
import core.typeClass.GetBarModel.{*, given}
import core.typeClass.GetBarVisualisation.{*, given}
import core.typeClass.{GetBarModel, GetBarVisualisation}
import core.useCase.VisualizeSortingUseCase
import mock.inputMock.VisualizeSortingInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil.assertRight

object VisualizeSortingUseCase_Test:

	object apply_should_return:

		def `VisualizeModel`: Unit =
			val res = Right(
				VisualizeSortingUseCase.getVisualizeModelBubbleSort(
					input = VisualizeSortingInputMock.input
				)
			)
			assertRight(res)(
				(res: VisualizeModel) =>
					val firstValue   = res.notStartedSorting.list.head.value
					val lastValue    = res.notStartedSorting.list.last.value
					val smallestValue = res.finishedSorting.list.head.value
					val biggestValue  = res.finishedSorting.list.last.value
					Seq(
						res.changes.head.list.head.value == firstValue,
						res.changes.head.list.last.value == lastValue,
						res.changes.last.list.head.value == smallestValue,
						res.changes.last.list.last.value == biggestValue,
						res.changes.head.list.head.barState == BarStateModel.Focused,
						res.changes.head.list.last.barState == BarStateModel.Normal,
						res.changes.last.list.head.barState == BarStateModel.Focused,
						res.changes.last.list.last.barState == BarStateModel.AlreadySorted,
						res.changes.head.list.length == res.changes.head.list.length,
						res.notStartedSorting.list.exists:
							case BarModel(_, barState) => barState == BarStateModel.Normal
						,
						res.finishedSorting.list.head.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.last.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.head.value == smallestValue,
						res.finishedSorting.list.last.value == biggestValue
					)
			)

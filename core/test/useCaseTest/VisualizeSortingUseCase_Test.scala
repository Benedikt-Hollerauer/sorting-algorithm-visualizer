package test.useCaseTest

import core.model.SortableModel
import core.useCase.VisualizeSortingUseCase
import mock.inputMock.VisualizeSortingInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil.*

object VisualizeSortingUseCase_Test:

	object apply_should_return:

		def `LazyList[SortableModel]`: Unit =
			val res = Right(
				VisualizeSortingUseCase(
					input = VisualizeSortingInputMock.input
				)
			)
			assertRight(res)(
				(res: LazyList[SortableModel]) =>
					val headList = res.head.valuesWithIndices.list
					val lastList = res.last.valuesWithIndices.list
					Seq(
						res.head == SortableModelMock.unsorted,
						res.last == SortableModelMock.sorted,
						headList.length == lastList.length
					)
			)
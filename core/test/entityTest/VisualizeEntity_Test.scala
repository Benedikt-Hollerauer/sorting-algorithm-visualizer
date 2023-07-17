package test.entityTest

import core.entity.VisualizeEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.{SortableModelMock, SortedModelMock}
import test.TestUtil.assertRight

object VisualizeEntity_Test:

	object getBarVisualisation_should_return:

		def `VisualizeModel`: Unit =
			val res = Right(
				VisualizeEntity.getBarVisualisation(
					sortedModel = SortedModelMock.sortedModel
				)
			)
			assertRight(res)(
				(res: VisualizeModel) =>
					Seq(
						res.changes.head.list.head.value == ToBeSortedMock.unsorted.head,
						res.changes.head.list.last.value == ToBeSortedMock.unsorted.last,
						res.changes.last.list.head.value == ToBeSortedMock.smallest,
						res.changes.last.list.last.value == ToBeSortedMock.biggest,
						res.changes.head.list.head.barState == BarStateModel.Focused,
						res.changes.head.list.last.barState == BarStateModel.Normal,
						res.changes.last.list.head.barState == BarStateModel.Focused,
						res.changes.last.list.last.barState == BarStateModel.AlreadySorted,
						res.changes.head.list.length == res.changes.head.list.length,
						res.finishedSorting.list.head.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.last.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.head.value == ToBeSortedMock.smallest,
						res.finishedSorting.list.last.value == ToBeSortedMock.biggest
					)
			)

	object swapSortable_should_return:

		def `SortableModel`: Unit =
			val firstValueWithIndex = SortableModelMock.unsorted.valuesWithIndices.list(0)
			val secondValueWithIndex = SortableModelMock.unsorted.valuesWithIndices.list(1)
			val res = VisualizeEntity.swapSortableValues(
				toBeUpdated = SortableModelMock.unsorted,
				swappedValues = (
					firstValueWithIndex,
					secondValueWithIndex
				)
			)
			val resList = res.valuesWithIndices.list
			assert(resList(0) == secondValueWithIndex)
			assert(resList(1) == firstValueWithIndex)
			assert(resList.length == SortableModelMock.unsorted.valuesWithIndices.list.length)
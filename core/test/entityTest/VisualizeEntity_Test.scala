package test.entityTest

import core.entity.VisualizeEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.{SortableModelMock, SortedModelMock}
import test.TestUtil.assertRight

object VisualizeEntity_Test:

	@main
	def visualizeEntityTest =
		getBarVisualisation_should_return.`LazyList[NonEmptyListModel[BarModel]]`

	object getBarVisualisation_should_return:

		def `LazyList[NonEmptyListModel[BarModel]]`: Unit =
			val res = Right(
				VisualizeEntity.getBarVisualisation(
					sortedModel = SortedModelMock.sortedModel
				)
			)
			println(res.value.last)
			assertRight(res)(
				(res: LazyList[NonEmptyListModel[BarModel]]) =>
					Seq(
						res.head.list.head.value == ToBeSortedMock.unsorted.head,
						res.head.list.last.value == ToBeSortedMock.unsorted.last,
						res.last.list.head.value == ToBeSortedMock.smallest,
						res.last.list.last.value == ToBeSortedMock.biggest,
						res.head.list.head.barColor == BarStateModel.Swapped,
						res.head.list.last.barColor == BarStateModel.Normal,
						res.last.list.head.barColor == BarStateModel.Swapped,
						res.last.list.last.barColor == BarStateModel.Normal,
						res.head.list.length == res.head.list.length
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
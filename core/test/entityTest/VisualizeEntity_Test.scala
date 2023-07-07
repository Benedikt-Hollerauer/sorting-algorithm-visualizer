package test.entityTest

import core.entity.VisualizeEntity
import core.model.{BarColorModel, BarModel, NonEmptyListModel, SortableModel, SortedModel}
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
			assertRight(res)(
				(res: LazyList[NonEmptyListModel[BarModel]]) =>
					Seq(
						res.head.list.head.value == ToBeSortedMock.unsorted.head,
						res.head.list.last.value == ToBeSortedMock.unsorted.last,
						res.last.list.head.value == ToBeSortedMock.smallest,
						res.last.list.last.value == ToBeSortedMock.biggest,
						//res.head.list.head.barColor == BarColorModel.Blue,
						//res.head.list.last.barColor == BarColorModel.Red,
						//res.last.list.head.barColor == BarColorModel.Blue,
						//res.last.list.last.barColor == BarColorModel.Red,
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
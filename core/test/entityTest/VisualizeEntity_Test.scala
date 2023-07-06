package test.entityTest

import core.entity.VisualizeEntity
import core.model.{BarColorModel, BarModel, NonEmptyListModel, SortableModel, SortedModel}
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
			res.value.foreach(println)
			assertRight(res)(
				(res: LazyList[NonEmptyListModel[BarModel]]) =>
					Seq(
						res.head.list.head.value == -2,
						res.head.list.last.value == -500,
						res.last.list.head.value == -500,
						res.last.list.last.value == 999999,
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
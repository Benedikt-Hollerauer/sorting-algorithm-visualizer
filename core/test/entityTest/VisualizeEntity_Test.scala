package test.entityTest

import core.entity.VisualizeEntity
import core.model.{BarModel, BarStateModel}
import mock.modelMock.SortableModelMock

object VisualizeEntity_Test:

	object getSpecialBars_should_return:

		def `NonEmptyListModel[BarModel] - BarStateModel.Normal`: Unit =
			val res = VisualizeEntity().getSpecialBars(
				SortableModelMock.unsorted,
				BarStateModel.Normal
			)
			assert(
				res.list.exists:
					case BarModel(_, barState) => barState == BarStateModel.Normal
			)

		def `NonEmptyListModel[BarModel] - BarStateModel.FinishedSorting`: Unit =
			val res = VisualizeEntity().getSpecialBars(
				SortableModelMock.unsorted,
				BarStateModel.FinishedSorting
			)
			assert(
				res.list.exists:
					case BarModel(_, barState) => barState == BarStateModel.FinishedSorting
			)


	object swapSortable_should_return:

		def `SortableModel`: Unit =
			val firstValueWithIndex = SortableModelMock.unsorted.list(0)
			val secondValueWithIndex = SortableModelMock.unsorted.list(1)
			val res = VisualizeEntity().swapSortableValues(
				toBeUpdated = SortableModelMock.unsorted,
				swappedValues = (
					firstValueWithIndex,
					secondValueWithIndex
				)
			)
			val resList = res.list
			assert(resList(0) == secondValueWithIndex)
			assert(resList(1) == firstValueWithIndex)
			assert(resList.length == SortableModelMock.unsorted.list.length)
package test.entityTest

import core.entity.VisualizeEntity
import mock.modelMock.SortableModelMock

object VisualizeEntity_Test:

	object swapSortable_should_return:

		def `SortableModel`: Unit =
			val firstValueWithIndex = SortableModelMock.unsorted.valuesWithIndices.list(0)
			val secondValueWithIndex = SortableModelMock.unsorted.valuesWithIndices.list(1)
			val res = VisualizeEntity.swapSortable(
				toBeUpdated = SortableModelMock.unsorted,
				swappedValues = (
					firstValueWithIndex,
					secondValueWithIndex
				)
			)
			val resList = res.valuesWithIndices.list
			println(SortableModelMock.unsorted.valuesWithIndices.list)
			println(resList)
			assert(resList(0) == secondValueWithIndex)
			assert(resList(1) == firstValueWithIndex)
			assert(resList.length == SortableModelMock.unsorted.valuesWithIndices.list.length)
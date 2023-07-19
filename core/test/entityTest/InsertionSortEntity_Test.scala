package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.{BubbleSortEntity, InsertionSortEntity}
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import test.TestUtil

object InsertionSortEntity_Test:

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = InsertionSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (636, 743),
				lastFocusedValues = (5, 196),
				sorted = SortableModelMock.sortedAscending
			)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = InsertionSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (636, 743),
				lastFocusedValues = (743, 662),
				sorted = SortableModelMock.sortedDescending
			)
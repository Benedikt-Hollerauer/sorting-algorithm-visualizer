package test.entityTest

import core.entity.{BubbleSortEntity, InsertionSortEntity}
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import test.TestUtil
import core.Util.toValuesWithIndices

object InsertionSortEntity_Test:

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = InsertionSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonBubbleSortEntitySortProperties(res, (false, false), (ToBeSortedMock.smallest, 2), (196, 7), ToBeSortedMock.descendingOrder.sorted.toValuesWithIndices.dropRight(2), SortableModelMock.sortedAscending)
			TestUtil.testIfEmptyValueWithIndexModelExists(res.changes.toList)
			TestUtil.testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = InsertionSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonBubbleSortEntitySortProperties(res, (true, false), (ToBeSortedMock.biggest, 1), (662, 6), ToBeSortedMock.ascendingOrder.sorted.toValuesWithIndices.dropRight(2), SortableModelMock.sortedDescending)
			TestUtil.testIfEmptyValueWithIndexModelExists(res.changes.toList)
			TestUtil.testIfEmptyIndexModelExists(res.changes.toList)
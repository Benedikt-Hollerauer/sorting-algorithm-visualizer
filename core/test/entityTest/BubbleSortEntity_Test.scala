package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import test.TestUtil

object BubbleSortEntity_Test:

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = BubbleSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonBubbleSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (636, 743),
				headFocusedIndicesChanged = false,
				lastFocusedValues = (5, 196),
				lastFocusedIndicesChanged = false,
				sorted = SortableModelMock.sortedAscending
			)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonBubbleSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (743, 636),
				headFocusedIndicesChanged = true,
				lastFocusedValues = (743, 662),
				lastFocusedIndicesChanged = false,
				sorted = SortableModelMock.sortedDescending
			)

	object sortOnce_should_return:

		def `List[SortingModel] - ascending`: Unit =
			val alreadySorted = List(ToBeSortedMock.biggest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				ordering = OrderModel.Ascending
			)
			TestUtil.testCommonPropertiesSortOnceBubbleSort(
				res,
				(false, true),
				(636, ToBeSortedMock.biggest),
				alreadySorted,
				ToBeSortedMock.ascendingOrder.sortedOnce
			)

		def `List[SortingModel] - descending`: Unit =
			val alreadySorted = List(ToBeSortedMock.smallest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				ordering = OrderModel.Descending
			)
			TestUtil.testCommonPropertiesSortOnceBubbleSort(
				res,
				(true, true),
				(743, ToBeSortedMock.smallest),
				alreadySorted,
				ToBeSortedMock.descendingOrder.sortedOnce
			)

		def `None`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = List.empty[ValueWithIndexModel],
				alreadySorted = List.empty[ValueWithIndexModel],
				ordering = OrderModel.Ascending
			)
			assert(res.isEmpty)
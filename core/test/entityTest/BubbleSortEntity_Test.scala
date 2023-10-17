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
				expectedLength = 3,
				headFocusedValues = (ToBeSortedMock.middle, ToBeSortedMock.biggest),
				headFocusedIndicesChanged = false,
				lastFocusedValues = (ToBeSortedMock.smallest, ToBeSortedMock.middle),
				lastFocusedIndicesChanged = true,
				sorted = SortableModelMock.sortedAscending
			)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonBubbleSortProperties(
				res = res,
				expectedLength = 3,
				headFocusedValues = (ToBeSortedMock.biggest, ToBeSortedMock.middle),
				headFocusedIndicesChanged = true,
				lastFocusedValues = (ToBeSortedMock.biggest, ToBeSortedMock.middle),
				lastFocusedIndicesChanged = false,
				sorted = SortableModelMock.sortedDescending
			)

	object sortOnce_should_return:

		def `List[SortingModel.BubbleSort] - ascending`: Unit =
			val alreadySorted = List(ToBeSortedMock.biggest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				ordering = OrderModel.Ascending
			)
			TestUtil.testCommonPropertiesSortOnceBubbleSort(
				res = res,
				focusedIndicesChangedHeadAndTail = (false, true),
				focusIndicesHeadAndTail = (2, ToBeSortedMock.biggest),
				alreadySorted = alreadySorted,
				sortedOnce = ToBeSortedMock.ascendingOrder.sortedOnce
			)

		def `List[SortingModel.BubbleSort] - descending`: Unit =
			val alreadySorted = List(ToBeSortedMock.smallest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				ordering = OrderModel.Descending
			)
			TestUtil.testCommonPropertiesSortOnceBubbleSort(
				res = res,
				focusedIndicesChangedHeadAndTail = (true, false),
				focusIndicesHeadAndTail = (ToBeSortedMock.biggest, ToBeSortedMock.smallest),
				alreadySorted = alreadySorted,
				sortedOnce = ToBeSortedMock.descendingOrder.sortedOnce
			)

		def `None`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = List.empty[ValueWithIndexModel],
				alreadySorted = List.empty[ValueWithIndexModel],
				ordering = OrderModel.Ascending
			)
			assert(res.isEmpty)
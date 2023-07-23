package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.{BubbleSortEntity, InsertionSortEntity}
import core.model.*
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

	object sortSubListOnce_should_return:

		@main
		def it = `List[SortingModel.InsertionSort] - ascending`

		def `List[SortingModel.InsertionSort] - ascending`: Unit =
			val subList = List(1, 3, 6, 2)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices.reverse),
				currentPivot = ValueWithIndexModel(6, IndexModel.fromUnsafe(2)),
				ordering = OrderModel.Ascending
			)
			res.foreach(x => println(x.focusedValues))
			assert(res.head.focusedValues._1.value == 2)
			assert(res.head.focusedValues._2.value == 6)
			assert(res.last.focusedValues._1.value == 3)
			assert(res.last.focusedValues._2.value == 2)
			assert(res.length == 3)
			assert(res.exists:
				case SortingModel.InsertionSort(_, currentPivot) => currentPivot.value == 6
			)

		def `List[SortingModel.InsertionSort] - descending`: Unit =
			val subList = List(6, 3, 1, 2)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices.reverse),
				currentPivot = ValueWithIndexModel(1, IndexModel.fromUnsafe(2)),
				ordering = OrderModel.Descending
			)
			assert(res.head.focusedValues._1.value == 2)
			assert(res.head.focusedValues._2.value == 1)
			assert(res.last.focusedValues._1.value == 1)
			assert(res.last.focusedValues._2.value == 2)
			assert(res.length == 1)
			assert(res.exists:
				case SortingModel.InsertionSort(_, currentPivot) => currentPivot.value == 1
			)
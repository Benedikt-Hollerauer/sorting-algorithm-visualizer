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
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(6, IndexModel.fromUnsafe(2)),
				ordering = OrderModel.Ascending
			)
			res.foreach(println)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 3,
				headFocusedValues = (2, 6),
				lastFocusedValues = (3, 2),
				currentPivotValue = 6
			)

		def `List[SortingModel.InsertionSort] - descending`: Unit =
			val subList = List(6, 3, 1, 2)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(1, IndexModel.fromUnsafe(2)),
				ordering = OrderModel.Descending
			)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 2,
				headFocusedValues = (2, 1),
				lastFocusedValues = (1, 2),
				currentPivotValue = 1
			)

		def `List[SortingModel.InsertionSort] - two elements`: Unit =
			val subList = List(3, 1)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(3, IndexModel.fromUnsafe(0)),
				ordering = OrderModel.Descending
			)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 2,
				headFocusedValues = (1, 3),
				lastFocusedValues = (3, 1),
				currentPivotValue = 3
			)
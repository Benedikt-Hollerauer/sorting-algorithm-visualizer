package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.InsertionSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import test.{TestUtil, Util_Test}

import scala.main

object InsertionSortEntity_Test:

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = InsertionSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 3,
				headFocusedValues = (ToBeSortedMock.middle, ToBeSortedMock.biggest),
				lastFocusedValues = (ToBeSortedMock.middle, ToBeSortedMock.smallest),
				sorted = SortableModelMock.sortedAscending
			)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = InsertionSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 2,
				headFocusedValues = (ToBeSortedMock.middle, ToBeSortedMock.biggest),
				lastFocusedValues = (ToBeSortedMock.middle, ToBeSortedMock.smallest),
				sorted = SortableModelMock.sortedDescending
			)

	object sortSubListOnce_should_return:

		def `List[SortingModel.InsertionSort] - ascending`: Unit =
			val subList = List(1, 3, 6, 2)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(6, IndexModel.fromUnsafe(2)),
				ordering = OrderModel.Ascending
			)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 3,
				headFocusedValues = (2, 6),
				lastFocusedValues = Some((2, 1)),
				currentPivotValue = 6,
				shouldBeSortedSubListOnce = subList.sorted
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
				lastFocusedValues = Some((2, 3)),
				currentPivotValue = 1,
				shouldBeSortedSubListOnce = subList.sorted(Ordering[Int].reverse)
			)

		def `List[SortingModel.InsertionSort] - two elements ascending`: Unit =
			val subList = List(3, 1)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(3, IndexModel.fromUnsafe(0)),
				ordering = OrderModel.Ascending
			)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 1,
				headFocusedValues = (1, 3),
				lastFocusedValues = None,
				currentPivotValue = 3,
				shouldBeSortedSubListOnce = subList.sorted
			)

		def `List[SortingModel.InsertionSort] - two elements descending`: Unit =
			val subList = List(3, 1)
			val res = InsertionSortEntity.sortSubListOnce(
				subList = SortableModel.fromUnsafe(subList.toValuesWithIndices),
				currentPivot = ValueWithIndexModel(3, IndexModel.fromUnsafe(0)),
				ordering = OrderModel.Descending
			)
			TestUtil.testCommonInsertionSortSortSublistOnceProperties(
				res = res,
				expectedLength = 1,
				headFocusedValues = (1, 3),
				lastFocusedValues = None,
				currentPivotValue = 3,
				shouldBeSortedSubListOnce = subList.sorted(Ordering[Int].reverse)
			)
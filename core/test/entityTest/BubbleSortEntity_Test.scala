package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import test.TestUtil

import scala.annotation.targetName
import scala.main

object BubbleSortEntity_Test:

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = BubbleSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonProperties(res, (false, false), (ToBeSortedMock.smallest, 2), (196, 7), ToBeSortedMock.descendingOrder.sorted.toValuesWithIndices.dropRight(2))
			TestUtil.testIfEmptyValueWithIndexModelExists(res.changes.toList)
			TestUtil.testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			TestUtil.testCommonProperties(res, (true, false), (ToBeSortedMock.biggest, 1), (662, 6), ToBeSortedMock.ascendingOrder.sorted.toValuesWithIndices.dropRight(2))
			TestUtil.testIfEmptyValueWithIndexModelExists(res.changes.toList)
			TestUtil.testIfEmptyIndexModelExists(res.changes.toList)

	object sortOnce_should_return:

		def `List[SortingModel] - ascending`: Unit =
			val alreadySorted = List(ToBeSortedMock.biggest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				comparator = OrderModel.Ascending
			)
			assert(res.get.map(_.focusedIndices._1.value) :+ res.get.last.focusedIndices._2.value == ToBeSortedMock.ascendingOrder.sortedOnce)
			TestUtil.testCommonPropertiesSortOnce(res, (false, true), (636, ToBeSortedMock.biggest), alreadySorted)

		def `List[SortingModel] - descending`: Unit =
			val alreadySorted = List(ToBeSortedMock.smallest).toValuesWithIndices
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				alreadySorted = alreadySorted,
				comparator = OrderModel.Descending
			)
			assert(res.get.map(_.focusedIndices._1.value) :+ res.get.last.focusedIndices._2.value == ToBeSortedMock.descendingOrder.sortedOnce)
			TestUtil.testCommonPropertiesSortOnce(res, (true, true), (743, ToBeSortedMock.smallest), alreadySorted)

		def `None`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = List.empty[ValueWithIndexModel],
				alreadySorted = List.empty[ValueWithIndexModel],
				comparator = OrderModel.Ascending
			)
			assert(res.isEmpty)
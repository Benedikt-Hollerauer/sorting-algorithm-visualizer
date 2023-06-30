package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

import scala.main

object BubbleSortEntity_Test:

	@main
	def it =
		sortOnce_should_return.`List[SortingModel] - ascending`

	private def testIfEmptyValueWithIndexModelExists(res: List[SortingModel]): Unit =
		assert(res.exists:
			case SortingModel((ValueWithIndexModel(value0, _), ValueWithIndexModel(value1, _)), _) => value0 != 0 && value1 != 0
		)

	private def testIfEmptyIndexModelExists(res: List[SortingModel]): Unit =
		assert(res.exists:
			case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		)

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = BubbleSortEntity.sortAscending(
				SortableModelMock.unsorted
			)
			assert(res.sortableModel == SortableModelMock.unsorted)
			assert(res.changes.last.focusedIndicesChanged == false)
			assert(res.changes.last.focusedIndices._1.value == -2)
			assert(res.changes.last.focusedIndices._1.indexModel.index == 0)
			assert(res.changes.last.focusedIndices._2.value == -500)
			assert(res.changes.last.focusedIndices._2.indexModel.index == 22)
			testIfEmptyValueWithIndexModelExists(res.changes.toList)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			assert(res.sortableModel == SortableModelMock.unsorted)
			assert(res.changes.last.focusedIndicesChanged == false)
			assert(res.changes.last.focusedIndices._1.value == 999999)
			assert(res.changes.last.focusedIndices._1.indexModel.index == 1)
			assert(res.changes.last.focusedIndices._2.value == 6587)
			assert(res.changes.last.focusedIndices._2.indexModel.index == 15)
			testIfEmptyValueWithIndexModelExists(res.changes.toList)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortOnce_should_return:

		private def testCommonProperties(
			res: List[SortingModel],
			focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
			focusIndicesHeadAndTail: (Int, Int)
		): Unit =
			assert(res.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
			assert(res.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
			assert(res.head.focusedIndices._1.value == focusIndicesHeadAndTail._1)
			assert(res.last.focusedIndices._2.value == focusIndicesHeadAndTail._2)
			testIfEmptyValueWithIndexModelExists(res)
			testIfEmptyIndexModelExists(res)

		def `List[SortingModel] - ascending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.unsorted.valuesWithIndices.list,
				comparator = OrderModel.Ascending
			)
			res.foreach(println)
			assert(res.map(_.focusedIndices._1.value) :+ 999999 == ToBeSortedMock.ascendingOrder.sortedOnce)
			testCommonProperties(res, (false, true), (-2, 999999))

		def `List[SortingModel] - descending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.unsorted.valuesWithIndices.list,
				comparator = OrderModel.Descending
			)
			assert(res.map(_.focusedIndices._1.value) :+ -500 == ToBeSortedMock.descendingOrder.sortedOnce)
			testCommonProperties(res, (true, false), (999999, -500))
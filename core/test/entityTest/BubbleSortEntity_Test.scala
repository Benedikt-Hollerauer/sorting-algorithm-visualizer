package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

import scala.main

object BubbleSortEntity_Test:

	@main
	def bubbleSortEntityTest =
		sortAscending_should_return.`SortedModel - ascending`

	private def testCommonProperties(
		res: SortedModel,
		focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
		focusedIndicesFirstValueWithIndex: (Int, Int),
		focusedIndicesSecondValueWithIndex: (Int, Int)
	): Unit =
		assert(res.sortableModel == SortableModelMock.unsorted)
		assert(res.changes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
		assert(res.changes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
		assert(res.changes.last.focusedIndices._1.value == focusedIndicesFirstValueWithIndex._1)
		assert(res.changes.last.focusedIndices._1.indexModel.index == focusedIndicesFirstValueWithIndex._2)
		assert(res.changes.last.focusedIndices._2.value == focusedIndicesSecondValueWithIndex._1)
		assert(res.changes.last.focusedIndices._2.indexModel.index == focusedIndicesSecondValueWithIndex._2)

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
			testCommonProperties(res, (false, false), (ToBeSortedMock.smallest, 2), (196, 7))
			testIfEmptyValueWithIndexModelExists(res.changes.toList)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			testCommonProperties(res, (true, false), (ToBeSortedMock.biggest, 1), (662, 6))
			testIfEmptyValueWithIndexModelExists(res.changes.toList)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortOnce_should_return:

		private def testCommonPropertiesSortOnce(
			res: Option[List[SortingModel]],
			focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
			focusIndicesHeadAndTail: (Int, Int)
		): Unit =
			val unwrappedRes = res.get
			assert(unwrappedRes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
			assert(unwrappedRes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
			assert(unwrappedRes.head.focusedIndices._1.value == focusIndicesHeadAndTail._1)
			assert(unwrappedRes.last.focusedIndices._2.value == focusIndicesHeadAndTail._2)
			testIfEmptyValueWithIndexModelExists(unwrappedRes)
			testIfEmptyIndexModelExists(unwrappedRes)

		def `List[SortingModel] - ascending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				comparator = OrderModel.Ascending
			)
			assert(res.get.map(_.focusedIndices._1.value) :+ res.get.last.focusedIndices._2.value == ToBeSortedMock.ascendingOrder.sortedOnce)
			testCommonPropertiesSortOnce(res, (false, true), (636, ToBeSortedMock.biggest))

		def `List[SortingModel] - descending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				comparator = OrderModel.Descending
			)
			assert(res.get.map(_.focusedIndices._1.value) :+ res.get.last.focusedIndices._2.value == ToBeSortedMock.descendingOrder.sortedOnce)
			testCommonPropertiesSortOnce(res, (true, true), (743, ToBeSortedMock.smallest))

		def `None`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = List.empty[ValueWithIndexModel],
				comparator = OrderModel.Ascending
			)
			assert(res.isEmpty)
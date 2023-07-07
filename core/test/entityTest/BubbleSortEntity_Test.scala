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
		sortOnce_should_return.`List[SortingModel] - descending`

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
			) //TODO use TestUtil.assertRight here | also the error lies here
			testCommonProperties(res, (false, true), (-500, 22), (-2, 0))
			testIfEmptyValueWithIndexModelExists(res.changes.toList)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortDescending(
				SortableModelMock.unsorted
			)
			testCommonProperties(res, (true, false), (999999, 1), (6587, 15))
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
			testCommonPropertiesSortOnce(res, (false, true), (-2, 999999))

		def `List[SortingModel] - descending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = ToBeSortedMock.unsorted.toValuesWithIndices,
				comparator = OrderModel.Descending
			)
			assert(res.get.map(_.focusedIndices._1.value) :+ -500 == ToBeSortedMock.descendingOrder.sortedOnce)
			testCommonPropertiesSortOnce(res, (true, false), (999999, -500))

		def `None`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = List.empty[ValueWithIndexModel],
				comparator = OrderModel.Ascending
			)
			assert(res.isEmpty)
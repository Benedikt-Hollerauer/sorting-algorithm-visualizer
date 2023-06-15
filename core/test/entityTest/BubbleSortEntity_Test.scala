package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

import scala.main

object BubbleSortEntity_Test:

	private def testIfEmptyIndexModelExists(res: List[SortingModel]): Unit =
		assert(res.exists:
			case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		)

	object sortAscending_should_return:

		def `SortedModel - ascending`: Unit =
			val res = BubbleSortEntity.sortAscending(
				SortableModelMock.sortable
			)
			assert(res.sortableModel == SortableModelMock.sortable)
			assert(res.changes.last.focusedIndicesChanged == true)
			assert(res.changes.last.focusedIndices._1.value == 999999)
			assert(res.changes.last.focusedIndices._1.indexModel.index == 2)
			assert(res.changes.last.focusedIndices._2.value == 3)
			assert(res.changes.last.focusedIndices._2.indexModel.index == 1)
			testIfEmptyIndexModelExists(res.changes.toList)

	object sortDescending_should_return:

		def `SortedModel - descending`: Unit =
			val res = BubbleSortEntity.sortAscending(
				SortableModelMock.sortable
			)
			assert(res.sortableModel == SortableModelMock.sortable)
			assert(res.changes.last.focusedIndicesChanged == true)
			assert(res.changes.last.focusedIndices._1.value == 999999)
			assert(res.changes.last.focusedIndices._1.indexModel.index == 2)
			assert(res.changes.last.focusedIndices._2.value == 3)
			assert(res.changes.last.focusedIndices._2.indexModel.index == 1)
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
			testIfEmptyIndexModelExists(res)

		def `List[SortingModel] - ascending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.sortable.valuesWithIndices.list,
				comparator = OrderModel.Ascending
			)
			testCommonProperties(res, (false, true), (-2, 999999))

		def `List[SortingModel] - descending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.sortable.valuesWithIndices.list,
				comparator = OrderModel.Descending
			)
			testCommonProperties(res, (true, false), (999999, -500))
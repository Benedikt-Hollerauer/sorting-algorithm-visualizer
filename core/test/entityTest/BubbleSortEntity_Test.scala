package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

import scala.main

object BubbleSortEntity_Test:

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
			assert(res.changes.exists: sortingModel =>
				sortingModel match
					case SortingModel(
						(ValueWithIndexModel(_, IndexModel(-1)), _),
						_
					) => false
					case _ => true
			)
			assert(res.changes.exists: sortingModel =>
				sortingModel match
					case SortingModel(
						(_, ValueWithIndexModel(_, IndexModel(-1))),
						_
					) => false
					case _ => true
			)

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
			assert(
				res.changes.exists: sortingModel =>
					sortingModel match
						case SortingModel(
							(ValueWithIndexModel(_, IndexModel(-1)), _),
							_
						) => false
						case _ => true
			)
			assert(
				res.changes.exists: sortingModel =>
					sortingModel match
						case SortingModel(
							(_, ValueWithIndexModel(_, IndexModel(-1))),
							_
						) => false
						case _ => true
			)

	object sortOnce_should_return:

		@main
		def it =
			this.`List[SortingModel] - ascending`

		def `List[SortingModel] - ascending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.sortable.valuesWithIndices.list,
				comparator = OrderModel.Ascending
			)
			//res.foreach(println)
			assert(res.head.focusedIndicesChanged == false)
			assert(res.last.focusedIndicesChanged == true)
			assert(res.head.focusedIndices._1.value == -2)
			assert(res.last.focusedIndices._2.value == 999999)

		def `List[SortingModel] - descending`: Unit =
			val res = BubbleSortEntity.sortOnce(
				toBeCompared = SortableModelMock.sortable.valuesWithIndices.list,
				comparator = OrderModel.Descending
			)
			assert(res.head.focusedIndicesChanged == false)
			assert(res.last.focusedIndicesChanged == false)
			assert(res.head.focusedIndices._1.value == -2)
			assert(res.last.focusedIndices._2.value == -500)
			//TODO: assert that there is no -1 IndexModel
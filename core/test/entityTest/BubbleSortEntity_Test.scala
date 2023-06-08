package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock

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
			//TODO: test if there is a index -1

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
			//TODO: test if there is a index -1
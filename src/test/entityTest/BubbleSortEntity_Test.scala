package test.entityTest

import core.entity.BubbleSortEntity
import core.value.SortableValue

object BubbleSortEntity_Test:

	object sortAscendingWithIntermediateResults_should_return:

		def `LazyList[Sortable](ascending)`: Unit =
			for
				sortableValueMock <- SortableValue.from(List(1, 0, 0, 2, 3, 4, 5, 3, 1, 2, 2, 0))
				res = BubbleSortEntity.sortAscendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield assert(res.last.list == List(0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 5) && res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[Sortable](descending)`: Unit =
			for
				sortableValueMock <- SortableValue.from(List(1, 0, 0, 2, 3, 4, 5, 3, 1, 2, 2, 0))
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield assert(res.last.list == List(0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 5) && res.length > 1)

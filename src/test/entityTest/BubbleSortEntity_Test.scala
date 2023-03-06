package test.entityTest

import core.entity.BubbleSortEntity
import core.value.SortableValue

object BubbleSortEntity_Test:

	object sortAscendingWithIntermediateResults_should_return:

		def `LazyList[Sortable](ascending)`: Unit =
			for
				sortableValueMock <- SortableValue.from(List(1, 3, 2, 5, 4))
				res = BubbleSortEntity.sortAscendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield assert(res.last.list == List(1, 2, 3, 4, 5) && res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[Sortable](descending)`: Unit =
			for
				sortableValueMock <- SortableValue.from(List(1, 3, 2, 5, 4))
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
				_ = res.foreach(println)
			yield assert(res.last.list == List(5, 4, 3, 2, 1) && res.length > 1)

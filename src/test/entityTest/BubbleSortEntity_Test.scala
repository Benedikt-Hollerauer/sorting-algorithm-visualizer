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
			yield assert(res.last.list == List(1, 2, 3, 4, 5))
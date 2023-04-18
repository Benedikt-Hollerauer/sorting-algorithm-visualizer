package test.entityTest

import core.entity.BubbleSortEntity
import core.model.{OrderModel, SortedModel}
import mock.ToBeSortedMock

object BubbleSortEntity_Test:

	object sortAscendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortAscendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield
				assert(res.last.list == ToBeSortedMock.ascendingOrder.sorted)
				assert(res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield
				assert(res.last.list == ToBeSortedMock.descendingOrder.sorted)
				assert(res.length > 1)

	object sortWithIntermediateResults_should_return:

		def `LazyList[Sortable](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Ascending
				)
			yield
				assert(res.last.list == ToBeSortedMock.ascendingOrder.sorted)
				assert(res.length > 1)

		def `LazyList[Sortable](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Descending
				)
			yield
				assert(res.last.list == ToBeSortedMock.descendingOrder.sorted)
				assert(res.length > 1)

	object sortOnceWithIntermediateResults_should_return:

		def `LazyList[Sortable](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortOnceWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Ascending
				)
			yield
				assert(res.last.list == ToBeSortedMock.ascendingOrder.sortedOnce)
				assert(res.length > 1)

		def `LazyList[Sortable](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortOnceWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Descending
				)
			yield
				assert(res.last.list == ToBeSortedMock.descendingOrder.sortedOnce)
				assert(res.length > 1)
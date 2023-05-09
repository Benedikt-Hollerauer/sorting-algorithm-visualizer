package test.entityTest

import core.entity.BubbleSortEntity
import core.model.{OrderModel, SortableModel}
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
				assert(res.last.sortable.list == ToBeSortedMock.ascendingOrder.sorted)
				assert(res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield
				assert(res.last.sortable.list == ToBeSortedMock.descendingOrder.sorted)
				assert(res.length > 1)

	object sortWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Ascending
				)
			yield
				assert(res.last.sortable.list == ToBeSortedMock.ascendingOrder.sorted)
				//TODO add assertion for changed indices
				assert(res.length > 1)

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Descending
				)
			yield
				assert(res.last.sortable.list == ToBeSortedMock.descendingOrder.sorted)
				assert(res.length > 1)

	object sortOnceWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortOnceWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Ascending
				)
			yield
				assert(res.last.sortable.list == ToBeSortedMock.ascendingOrder.sortedOnce)
				assert(res.length > 1)

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortOnceWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Descending
				)
			yield
				assert(res.last.sortable.list == ToBeSortedMock.descendingOrder.sortedOnce)
				assert(res.length > 1)

	object sortOnceByOrdering_should_return:

		def `(List[Int], List[Int])(ascending)`: Unit =
			for
				res <- BubbleSortEntity.sortOnceByOrdering(
					toBeSorted = (
						(List(1), List(0)),
						(2, 1)
					),
					ordering = OrderModel.Ascending
				)
			yield
				assert(res == (List(1, 2), List(0, 1)))

		def `(List[Int], List[Int])(descending)`: Unit =
			for
				res <- BubbleSortEntity.sortOnceByOrdering(
					toBeSorted = (
						(List(1), List(0)),
						(2, 0)
					),
					ordering = OrderModel.Descending
				)
			yield
				assert(res == (List(2, 1), List(0, 1)))
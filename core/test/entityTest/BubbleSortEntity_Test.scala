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

		def `List[Int](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceByOrdering(
				acc = List(3),
				next = 1,
				ordering = OrderModel.Ascending
			)
			assert(res == List(1, 3))

		def `List[Int](descending)`: Unit =
			val res = BubbleSortEntity.sortOnceByOrdering(
				acc = List(3),
				next = 1,
				ordering = OrderModel.Descending
			)
			assert(res == List(3, 1))
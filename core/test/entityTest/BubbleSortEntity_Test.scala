package test.entityTest

import core.entity.BubbleSortEntity
import core.model.{OrderModel, SortableModel, ValueWithIndex}
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
				assert(res.last.sortableWithIndex.head.value == -500)
				assert(res.last.sortableWithIndex.last.value == 999999)
				assert(res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield
				assert(res.last.sortableWithIndex.head.value == 999999)
				assert(res.last.sortableWithIndex.last.value == -500)
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
				assert(res.last.sortableWithIndex.head.value == -500)
				assert(res.last.sortableWithIndex.last.value == 999999)
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
				assert(res.last.sortableWithIndex.head.value == 999999)
				assert(res.last.sortableWithIndex.last.value == -500)
				assert(res.length > 1)

	object sortOnceWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.ascendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndex.from(value, index).toOption.get),
				ordering = OrderModel.Ascending
			)
			assert(res.last.sortableWithIndex.head.value == -2)
			assert(res.last.sortableWithIndex.last.value == 999999)
			assert(res.length > 1)

		def `LazyList[SortedModel](descending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.descendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndex.from(value, index).toOption.get),
				ordering = OrderModel.Descending
			)
			println(res.last)
			assert(res.last.sortableWithIndex.head.value == 999999)
			assert(res.last.sortableWithIndex.last.value == -500)
			assert(res.length > 1)

	object sortOnceByOrdering_should_return:

		private val accMock = ValueWithIndex.from(3, 0).toOption.get

		private val nextMock = ValueWithIndex.from(1, 1).toOption.get

		def `List[Int](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceByOrdering(
				acc = List(accMock),
				next = nextMock,
				ordering = OrderModel.Ascending
			)
			assert(res == List(
				nextMock,
				accMock
			))

		def `List[Int](descending)`: Unit =
			val res = BubbleSortEntity.sortOnceByOrdering(
				acc = List(accMock),
				next = nextMock,
				ordering = OrderModel.Descending
			)
			assert(res == List(
				accMock,
				nextMock
			))
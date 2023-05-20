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
				assert(res.last.sortableWithIndex == List(ValueWithIndex(-500,22), ValueWithIndex(-2,0), ValueWithIndex(-1,21), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(1,3), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(76,11), ValueWithIndex(84,13), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(1234,19), ValueWithIndex(6578,18), ValueWithIndex(6587,15), ValueWithIndex(999999,1)))
				assert(res.length > 1)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield
				assert(res.last.sortableWithIndex == List(ValueWithIndex(999999,1), ValueWithIndex(6587,15), ValueWithIndex(6578,18), ValueWithIndex(1234,19), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(84,13), ValueWithIndex(76,11), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(1,3), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(-1,21), ValueWithIndex(-2,0), ValueWithIndex(-500,22)))
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
				assert(res.last.sortableWithIndex == List(ValueWithIndex(-500,22), ValueWithIndex(-2,0), ValueWithIndex(-1,21), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(1,3), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(76,11), ValueWithIndex(84,13), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(1234,19), ValueWithIndex(6578,18), ValueWithIndex(6587,15), ValueWithIndex(999999,1)))
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
				assert(res.last.sortableWithIndex == List(ValueWithIndex(999999,1), ValueWithIndex(6587,15), ValueWithIndex(6578,18), ValueWithIndex(1234,19), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(84,13), ValueWithIndex(76,11), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(1,3), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(-1,21), ValueWithIndex(-2,0), ValueWithIndex(-500,22)))
				assert(res.length > 1)

	object sortOnceWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.ascendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndex(value, index)),
				ordering = OrderModel.Ascending
			)
			assert(res.last.sortableWithIndex == List(ValueWithIndex(-2,0), ValueWithIndex(123,2), ValueWithIndex(1,3), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(134,8), ValueWithIndex(564,9), ValueWithIndex(123,10), ValueWithIndex(76,11), ValueWithIndex(234,12), ValueWithIndex(84,13), ValueWithIndex(234,14), ValueWithIndex(6587,15), ValueWithIndex(234,16), ValueWithIndex(134,17), ValueWithIndex(6578,18), ValueWithIndex(1234,19), ValueWithIndex(564,20), ValueWithIndex(-1,21), ValueWithIndex(-500,22), ValueWithIndex(999999,1)))
			assert(res.length > 1)

		def `LazyList[SortedModel](descending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.descendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndex(value, index)),
				ordering = OrderModel.Descending
			)
			assert(res.last.sortableWithIndex == List(ValueWithIndex(999999,1), ValueWithIndex(123,2), ValueWithIndex(1,3), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(134,8), ValueWithIndex(564,9), ValueWithIndex(123,10), ValueWithIndex(76,11), ValueWithIndex(234,12), ValueWithIndex(84,13), ValueWithIndex(234,14), ValueWithIndex(6587,15), ValueWithIndex(234,16), ValueWithIndex(134,17), ValueWithIndex(6578,18), ValueWithIndex(1234,19), ValueWithIndex(564,20), ValueWithIndex(-1,21), ValueWithIndex(-2,0), ValueWithIndex(-500,22)))
			assert(res.length > 1)

	object sortOnceByOrdering_should_return:

		private val accMock = ValueWithIndex(3, 0)

		private val nextMock = ValueWithIndex(1, 1)

		def `List[Int](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceByOrdering(
				acc = List(accMock),
				next = nextMock,
				ordering = OrderModel.Ascending
			)
			assert(res == List(
				ValueWithIndex(1, 1),
				ValueWithIndex(3, 0)
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
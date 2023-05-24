package test.entityTest

import core.entity.BubbleSortEntity
import core.model.{OrderModel, SortableModel, SortedModel, ValueWithIndexModel}
import mock.ToBeSortedMock

object BubbleSortEntity_Test:

	private def assertCommonProperties(
		res: LazyList[SortedModel],
		first: Int,
		last: Int,
		focusedIndices: List[Int],
		changed: Boolean
	): Unit =
		assert(res.last.sortableWithIndex.head.value == first)
		assert(res.last.sortableWithIndex.last.value == last)
		assert(res.last.focusedIndices == focusedIndices)
		assert(res.last.focusedIndicesChanged == changed)
		assert(res.length > 1)

	object sortAscendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortAscendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield assertCommonProperties(
				res = res,
				first = -500,
				last = 999999,
				focusedIndices = List(15, 1),
				changed = false
			)

	object sortDescendingWithIntermediateResults_should_return:

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortDescendingWithIntermediateResults(
					toBeSorted = sortableValueMock
				)
			yield assertCommonProperties(
				res = res,
				first = 999999,
				last = -500,
				focusedIndices = List(0, 22),
				changed = false
			)

	object sortWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Ascending
				)
			yield assertCommonProperties(
				res = res,
				first = -500,
				last = 999999,
				focusedIndices = List(15, 1),
				changed = false
			)

		def `LazyList[SortedModel](descending)`: Unit =
			for
				sortableValueMock <- SortableModel.from(ToBeSortedMock.descendingOrder.unsorted)
				res = BubbleSortEntity.sortWithIntermediateResults(
					toBeSorted = sortableValueMock,
					ordering = OrderModel.Descending
				)
			yield assertCommonProperties(
				res = res,
				first = 999999,
				last = -500,
				focusedIndices = List(0, 22),
				changed = false
			)

	object sortOnceWithIntermediateResults_should_return:

		def `LazyList[SortedModel](ascending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.ascendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndexModel.from(value, index).toOption.get),
				ordering = OrderModel.Ascending
			)
			assertCommonProperties(
				res = res,
				first = -2,
				last = 999999,
				focusedIndices = List(1, 22),
				changed = true
			)

		def `LazyList[SortedModel](descending)`: Unit =
			val res = BubbleSortEntity.sortOnceWithIntermediateResults(
				toBeSorted = ToBeSortedMock.descendingOrder.unsorted.zipWithIndex.map((value, index) => ValueWithIndexModel.from(value, index).toOption.get),
				ordering = OrderModel.Descending
			)
			assertCommonProperties(
				res = res,
				first = 999999,
				last = -500,
				focusedIndices = List(0, 22),
				changed = false
			)

	object swapByOrdering_should_return:

		private val accMock = ValueWithIndexModel.from(3, 0).toOption.get

		private val nextMock = ValueWithIndexModel.from(1, 1).toOption.get

		def `SortedModel(ascending)`: Unit =
			val res = BubbleSortEntity.swapByOrdering(
				acc = SortedModel.from(
					List(accMock),
					List(0),
					false
				).toOption.get,
				next = nextMock,
				ordering = OrderModel.Ascending
			)
			assert(res.focusedIndices == List(0, 1))
			assert(res.focusedIndicesChanged == true)
			assert(res ==
				SortedModel.from(
					List(
						nextMock,
						accMock
					),
					List(
						accMock.index,
						nextMock.index
					),
					true
				).toOption.get
			)

		def `SortedModel(descending)`: Unit =
			val res = BubbleSortEntity.swapByOrdering(
				acc = SortedModel.from(
					List(accMock),
					List(0),
					false
				).toOption.get,
				next = nextMock,
				ordering = OrderModel.Descending
			)
			assert(res.focusedIndices == List(0, 1))
			assert(res.focusedIndicesChanged == false)
			assert(res ==
				SortedModel.from(
					List(
						accMock,
						nextMock
					),
					List(
						accMock.index,
						nextMock.index
					),
					false
				).toOption.get
			)
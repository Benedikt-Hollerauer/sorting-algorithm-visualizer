package test.modelTest

import mock.ToBeSortedMock
import scala.util.Random

object SortedModel_Test:

	private val correctChangedIndicesMock: List[Int] = List(0, 1)

	object from_should_return:

		def `SortedModel`: Unit =
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted).toOption.get,
					mayBeChangedIndices = correctChangedIndicesMock
				)
			yield
				assert(res.list == ToBeSortedMock.ascendingOrder.sorted)
				assert(res.changedIndices == correctChangedIndicesMock)

		def `ToFewChangedIndices`: Unit =
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted).toOption.get,
					mayBeChangedIndices = List.empty
				).left
			yield assert(res == SortedModelError.ToFewChangedIndices(List.empty))
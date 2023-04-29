package test.modelTest

import core.model.SortableModel
import error.modelError.SortedModelError
import mock.ToBeSortedMock

object SortedModel_Test:

	object from_should_return:

		def `SortedModel`: Unit =
			val correctChangedIndicesMock = List(0, 1)
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.sorted).toOption.get,
					mayBeChangedIndices = correctChangedIndicesMock
				)
				_ = println(res)
			yield
				assert(res.sortable.list == ToBeSortedMock.ascendingOrder.sorted)
				assert(res.changedIndices == correctChangedIndicesMock)

		def `ToFewChangedIndices`: Unit =
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted).toOption.get,
					mayBeChangedIndices = List.empty
				).left
			yield assert(res == SortedModelError.ToFewChangedIndices(List.empty))


		def `NegativeChangedIndices`: Unit =
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted).toOption.get,
					mayBeChangedIndices = List(-1, 0)
				).left
			yield assert(res == SortedModelError.NegativeChangedIndices(List(-1, 0)))
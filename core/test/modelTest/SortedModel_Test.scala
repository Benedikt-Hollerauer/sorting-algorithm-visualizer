package test.modelTest

import core.model.SortableModel
import error.modelError.SortedModelError
import mock.ToBeSortedMock

object SortedModel_Test:

	object from_should_return:

		private val sortableMock = (
			SortableModel.from(ToBeSortedMock.ascendingOrder.sorted).toOption.get,
			ToBeSortedMock.ascendingOrder.sorted
		)

		def `SortedModel`: Unit =
			val correctFocusedIndicesMock = List(0, 1)
			for
				res <- core.model.SortedModel.from(
					sortable = sortableMock._1,
					mayBeFocusedIndices = correctFocusedIndicesMock,
					focusedIndicesChanged = true
				)
			yield
				assert(res.sortable.list == sortableMock._2)
				assert(res.focusedIndices == correctFocusedIndicesMock)

		def `ToFewChangedIndices`: Unit =
			val toFewFocusedIndicesMock = List(0)
			for
				res <- core.model.SortedModel.from(
					sortable = sortableMock._1,
					mayBeFocusedIndices = toFewFocusedIndicesMock,
					focusedIndicesChanged = true
				)
			yield
				assert(res == SortedModelError.ToFewChangedIndices(toFewFocusedIndicesMock))

		def `NegativeChangedIndices`: Unit =
			val negativeFocusedIndicesMock = List(0, -1)
			for
				res <- core.model.SortedModel.from(
					sortable = sortableMock._1,
					mayBeFocusedIndices = toFewFocusedIndicesMock,
					focusedIndicesChanged = true
				)
			yield
				assert(res == SortedModelError.NegativeChangedIndices(negativeFocusedIndicesMock))
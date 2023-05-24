package test.modelTest

import core.model.{IndexModel, SortableModel, ValueWithIndexModel}
import error.modelError.SortedModelError
import mock.ToBeSortedMock
import core.Util.toValuesWithIndices

object SortedModel_Test:

	object from_should_return:

		private val sortableMock = (
			SortableModel.from(
				ToBeSortedMock.ascendingOrder
					.sorted
					.toValuesWithIndices
			).toOption.get,
			-500,
			999999
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
				assert(res.sortableWithIndex.head.value == sortableMock._2)
				assert(res.sortableWithIndex.last.value == sortableMock._3)
				assert(res.focusedIndices == correctFocusedIndicesMock)

		def `ToFewChangedIndices`: Unit =
			val toFewFocusedIndicesMock = List(0)
			for
				res <- core.model.SortedModel.from(
					sortable = sortableMock._1,
					mayBeFocusedIndices = toFewFocusedIndicesMock,
					focusedIndicesChanged = true
				).left
			yield
				assert(res == SortedModelError.ToFewChangedIndices(toFewFocusedIndicesMock))

		def `NegativeChangedIndices`: Unit =
			val negativeFocusedIndicesMock = List(0, -1)
			for
				res <- core.model.SortedModel.from(
					sortable = sortableMock._1,
					mayBeFocusedIndices = negativeFocusedIndicesMock,
					focusedIndicesChanged = true
				).left
			yield
				assert(res == SortedModelError.NegativeChangedIndices(negativeFocusedIndicesMock))
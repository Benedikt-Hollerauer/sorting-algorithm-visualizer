package test.modelTest

import core.model.{SortableModel, ValueWithIndex}
import error.modelError.SortedModelError
import mock.ToBeSortedMock

object SortedModel_Test:

	object from_should_return:

		private val sortableMock = (
			SortableModel.from(ToBeSortedMock.ascendingOrder.sorted).toOption.get,
			List(ValueWithIndex(-500,0), ValueWithIndex(-2,1), ValueWithIndex(-1,2), ValueWithIndex(0,3), ValueWithIndex(0,4), ValueWithIndex(1,5), ValueWithIndex(6,6), ValueWithIndex(6,7), ValueWithIndex(76,8), ValueWithIndex(84,9), ValueWithIndex(123,10), ValueWithIndex(123,11), ValueWithIndex(134,12), ValueWithIndex(134,13), ValueWithIndex(234,14), ValueWithIndex(234,15), ValueWithIndex(234,16), ValueWithIndex(564,17), ValueWithIndex(564,18), ValueWithIndex(1234,19), ValueWithIndex(6578,20), ValueWithIndex(6587,21), ValueWithIndex(999999,22))
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
				assert(res.sortableWithIndex == sortableMock._2)
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
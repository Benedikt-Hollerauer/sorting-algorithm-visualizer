package test.modelTest

import core.Util.toValuesWithIndices
import core.model.{IndexModel, NonEmptyListModel, SortableModel, ValueWithIndexModel}
import error.modelError.{NonEmptyListModelError, SortedModelError}
import mock.ToBeSortedMock
import test.TestUtil.*

object SortedModel_Test:

	object from_should_return:

		private val sortableMock = (
			SortableModel.from(
				NonEmptyListModel.from(
					ToBeSortedMock.ascendingOrder
						.sorted
						.toValuesWithIndices
				).toOption.get
			).toOption.get,
			-500,
			999999
		)

		def `SortedModel`: Unit =
			val correctFocusedIndicesMock = List(0, 1)
			val res = core.model.SortedModel.from(
				sortable = sortableMock._1,
				mayBeFocusedIndices = correctFocusedIndicesMock,
				focusedIndicesChanged = true
			)
			assertRight(res)(
				(res: core.model.SortedModel) => Seq(
					res.sortableWithIndex.head.value == sortableMock._2,
					res.sortableWithIndex.last.value == sortableMock._3,
					res.focusedIndices == correctFocusedIndicesMock
				)
			)

		def `ToFewChangedIndices`: Unit =
			val toFewFocusedIndicesMock = List(0)
			val res = core.model.SortedModel.from(
				sortable = sortableMock._1,
				mayBeFocusedIndices = toFewFocusedIndicesMock, // NonEmptyListModel[IndexModel]
				focusedIndicesChanged = true
			)
			assertLeft(res)(SortedModelError.ToFewChangedIndices(toFewFocusedIndicesMock))

		def `NegativeChangedIndices`: Unit =
			val negativeFocusedIndicesMock = List(0, -1)
			val res = core.model.SortedModel.from(
				sortable = sortableMock._1,
				mayBeFocusedIndices = negativeFocusedIndicesMock,
				focusedIndicesChanged = true
			)
			assertLeft(res)(SortedModelError.NegativeChangedIndices(negativeFocusedIndicesMock))
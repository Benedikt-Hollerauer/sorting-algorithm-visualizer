package mock.inputMock

import core.input.SortByInsertionSortInput
import core.model.OrderModel
import mock.modelMock.SortableModelMock

object SortByInsertionSortInputMock:

	val ascendingOrder = SortByInsertionSortInput(
		toBeSorted = SortableModelMock.unsorted,
		ordering = OrderModel.Ascending
	)

	val descendingOrder = ascendingOrder.copy(
		ordering = OrderModel.Descending
	)
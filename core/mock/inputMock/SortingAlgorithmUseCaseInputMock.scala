package mock.inputMock

import core.input.SortingAlgorithmUseCaseInput
import core.model.OrderModel
import mock.modelMock.SortableModelMock

object SortingAlgorithmUseCaseInputMock:

	val ascendingOrder = SortingAlgorithmUseCaseInput(
		toBeSorted = SortableModelMock.unsorted,
	ordering = OrderModel.Ascending
	)

	val descendingOrder = ascendingOrder.copy(
		ordering = OrderModel.Descending
	)
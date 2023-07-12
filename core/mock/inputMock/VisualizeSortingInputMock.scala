package mock.inputMock

import core.input.VisualizeSortingInput
import core.model.OrderModel
import mock.modelMock.SortedModelMock

object VisualizeSortingInputMock:

	val input: VisualizeSortingInput = VisualizeSortingInput(
		sortedModel = SortedModelMock.sortedModel
	)
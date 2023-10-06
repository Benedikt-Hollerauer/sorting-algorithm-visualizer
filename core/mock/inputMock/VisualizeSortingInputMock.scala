package mock.inputMock

import core.input.VisualizeSortingInput
import core.model.{OrderModel, SortingModel}
import mock.modelMock.SortedModelMock

object VisualizeSortingInputMock:

	val input: VisualizeSortingInput[SortingModel.BubbleSort] = VisualizeSortingInput(
		sortedModel = SortedModelMock.sortedModelBubbleSort
	)
package mock.inputMock

import core.input.VisualizeSortingInput
import mock.modelMock.SortedModelMock

object VisualizeSortingInputMock:

	val input: VisualizeSortingInput = VisualizeSortingInput(
		sortedModel = SortedModelMock.sortedModel
	)
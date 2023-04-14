package mock.inputMock

import core.input.GenerateSortableInput

object GenerateSortableInputMock:

	val success = GenerateSortableInput(
		from = 0,
		to = 50,
		minSize = 5,
		maxSize = 750
	)

	val emptyListFailure = success.copy(
		from = 0,
		to = 0
	)

	val toFewElementsFailure = success.copy(
		from = 0,
		to = 1
	)

	val toManyElementsFailure = success.copy(
		to = 1000
	)
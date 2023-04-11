package mock.inputMock

import core.input.GenerateSortableInput

object GenerateSortableInputMock:

	val success = GenerateSortableInput(
		from = ???,
		to = ???,
		minSize = ???,
		maxSize = ???
	)

	val emptyListFailure = success.copy(

	)

	val toFewElementsFailure = success.copy(

	)

	val toManyElementsFailure = success.copy(

	)
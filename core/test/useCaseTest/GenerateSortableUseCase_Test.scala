package test.useCaseTest

object GenerateSortableUseCase_Test:

	object apply_should_return:

		def `Sortable`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock
			)
			assert(res.isRight)

		def `InputFailure[EmptyList]`: Unit =
			for
				res <- GenerateSortableUseCase(
					input = GenerateSortableInputMock
				).left
			yield assert(
				res == SortByBubbleSortUseCaseError.InputFailure(
					SortableModelError.EmptyList
				)
			)

		def `InputFailure[ToFewElements]`: Unit =
			for
				res <- GenerateSortableUseCase(
					input = GenerateSortableInputMock
				).left
			yield assert(
				res == SortByBubbleSortUseCaseError.InputFailure(
					SortableModelError.ToFewElements(
						List(1)
					)
				)
			)
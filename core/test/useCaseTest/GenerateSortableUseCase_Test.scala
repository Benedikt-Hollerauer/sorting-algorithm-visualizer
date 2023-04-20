package test.useCaseTest

import core.useCase.GenerateSortableUseCase
import error.modelError.SortableModelError
import error.useCaseError.GenerateSortableUseCaseError
import mock.inputMock.GenerateSortableInputMock

object GenerateSortableUseCase_Test:

	object apply_should_return:

		def `SortableModel`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.success
			)
			assert(res.isRight)

		def `InputFailure[EmptyList]`: Unit =
			for
				res <- GenerateSortableUseCase(
					input = GenerateSortableInputMock.emptyListFailure
				).left
			yield assert(
				res == GenerateSortableUseCaseError.InputFailure(
					SortableModelError.EmptyList
				)
			)

		def `InputFailure[ToFewElements]`: Unit =
			for
				res <- GenerateSortableUseCase(
					input = GenerateSortableInputMock.toFewElementsFailure
				).left
			yield assert(
				res == GenerateSortableUseCaseError.InputFailure(
					SortableModelError.ToFewElements(1)
				)
			)

		def `InputFailure[ToManyElements]`: Unit =
			for
				res <- GenerateSortableUseCase(
					input = GenerateSortableInputMock.toManyElementsFailure
				).left
			yield assert(
				res == GenerateSortableUseCaseError.InputFailure(
					SortableModelError.ToManyElements(1000)
				)
			)
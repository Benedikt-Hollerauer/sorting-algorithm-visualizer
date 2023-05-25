package test.useCaseTest

import core.model.SortableModel
import core.useCase.GenerateSortableUseCase
import error.modelError.SortableModelError
import error.useCaseError.GenerateSortableUseCaseError
import mock.inputMock.GenerateSortableInputMock
import test.TestUtil.*

object GenerateSortableUseCase_Test:

	object apply_should_return:

		def `SortableModel`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.success
			)
			assertRight(res)(
				(res: SortableModel) => Seq(
					res.valuesWithIndices.head.indexModel.index == 0,
					res.valuesWithIndices.last.indexModel.index == 49
				)
			)

		def `InputFailure[EmptyList]`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.emptyListFailure
			)
			assertLeft(res)(
				GenerateSortableUseCaseError.InputFailure(
					SortableModelError.EmptyList
				)
			)

		def `InputFailure[ToFewElements]`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.toFewElementsFailure
			)
			assertLeft(res)(
				GenerateSortableUseCaseError.InputFailure(
					SortableModelError.ToFewElements(1)
				)
			)

		def `InputFailure[ToManyElements]`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.toManyElementsFailure
			)
			assertLeft(res)(
				GenerateSortableUseCaseError.InputFailure(
					SortableModelError.ToManyElements(1000)
				)
			)
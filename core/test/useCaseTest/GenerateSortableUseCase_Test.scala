package test.useCaseTest

import core.model.SortableModel
import core.useCase.GenerateSortableUseCase
import error.modelError.{NonEmptyListModelError, SortableModelError}
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
					res.valuesWithIndices.list.head.indexModel.index == 0,
					res.valuesWithIndices.list.last.indexModel.index == 49
				)
			)

		def `NonEmptyListModelCreationFailed[EmptyList]`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.emptyListFailure
			)
			assertLeft(res)(
				GenerateSortableUseCaseError.NonEmptyListModelCreationFailed(
					NonEmptyListModelError.ToFewElements
				)
			)

		def `SortableModelCreationFailed[ToManyElements]`: Unit =
			val res = GenerateSortableUseCase(
				input = GenerateSortableInputMock.toManyElementsFailure
			)
			assertLeft(res)(
				GenerateSortableUseCaseError.SortableModelCreationFailed(
					SortableModelError.ToManyElements(1000)
				)
			)
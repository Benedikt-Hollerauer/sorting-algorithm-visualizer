package test.useCaseTest

import core.model.{SortableModel, ValueWithIndexModel}
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
				(res: SortableModel[ValueWithIndexModel]) => Seq(
					res.list.head.indexModel.index == 0,
					res.list.last.indexModel.index == 49
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
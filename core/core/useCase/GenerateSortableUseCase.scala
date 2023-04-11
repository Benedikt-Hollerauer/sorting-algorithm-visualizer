package core.useCase

import core.input.GenerateSortableInput
import core.model.SortableModel
import error.useCaseError.GenerateSortableUseCaseError

object GenerateSortableUseCase:

	def apply(input: GenerateSortableInput): Either[GenerateSortableUseCaseError, SortableModel] = ???
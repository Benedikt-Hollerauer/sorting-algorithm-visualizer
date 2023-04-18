package core.useCase

import core.input.GenerateSortableInput
import core.model.SortedModel
import error.useCaseError.GenerateSortableUseCaseError

import scala.util.Random

object GenerateSortableUseCase:

	def apply(input: GenerateSortableInput): Either[GenerateSortableUseCaseError, SortedModel] =
		val toBeSorted = List.fill((input.from to input.to).length - 1)(Random.between(input.minSize, input.maxSize))
		for
			sortable <- SortedModel.from(toBeSorted)
				.left
				.map(sortableModelError => GenerateSortableUseCaseError.InputFailure(sortableModelError))
		yield sortable
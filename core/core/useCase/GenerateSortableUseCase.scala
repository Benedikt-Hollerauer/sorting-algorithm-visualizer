package core.useCase

import core.input.GenerateSortableInput
import core.model.{SortableModel, SortedModel}
import error.useCaseError.GenerateSortableUseCaseError

import scala.util.Random

object GenerateSortableUseCase:

	def apply(input: GenerateSortableInput): Either[GenerateSortableUseCaseError, SortableModel] =
		val toBeSorted = List.fill((input.from to input.to).length - 1)(Random.between(input.minSize, input.maxSize))
		for
			sortable <- SortableModel.from(toBeSorted)
				.left
				.map: sortableModelError =>
					GenerateSortableUseCaseError.InputFailure(sortableModelError)
		yield sortable
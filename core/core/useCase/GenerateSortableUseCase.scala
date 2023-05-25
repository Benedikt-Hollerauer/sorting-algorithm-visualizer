package core.useCase

import core.Util.toValuesWithIndices
import core.input.GenerateSortableInput
import core.model.{IndexModel, SortableModel, SortedModel, ValueWithIndexModel}
import error.useCaseError.GenerateSortableUseCaseError

import scala.util.Random

object GenerateSortableUseCase:

	def apply(input: GenerateSortableInput): Either[GenerateSortableUseCaseError, SortableModel] =
		val toBeSorted = List.fill((input.from to input.to).length - 1)(Random.between(input.minSize, input.maxSize))
		for
			sortable <- SortableModel.from(
				toBeSorted.toValuesWithIndices
			)
				.left
				.map: sortableModelError =>
					GenerateSortableUseCaseError.InputFailure(sortableModelError)
		yield sortable
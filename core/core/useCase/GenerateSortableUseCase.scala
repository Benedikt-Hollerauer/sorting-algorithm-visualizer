package core.useCase

import core.Util.toValuesWithIndices
import core.input.GenerateSortableInput
import core.model.*
import error.useCaseError.GenerateSortableUseCaseError

import scala.util.Random

object GenerateSortableUseCase:

	def apply(input: GenerateSortableInput): Either[GenerateSortableUseCaseError, SortableModelOld] =
		val toBeSorted = List.fill((input.from to input.to).length - 1)(Random.between(input.minSize, input.maxSize))
		for
			mayBeNonEmptyList <- SortableModel.from(
				toBeSorted.toValuesWithIndices
			).left
				.map: nonEmptyListModelError =>
					GenerateSortableUseCaseError.NonEmptyListModelCreationFailed(nonEmptyListModelError)
			sortable <- SortableModelOld.from(
				mayBeNonEmptyList
			).left
				.map: sortableModelError =>
					GenerateSortableUseCaseError.SortableModelCreationFailed(sortableModelError)
		yield sortable
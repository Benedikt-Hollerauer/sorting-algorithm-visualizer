package core.useCase

import core.Contracts.SortingAlgorithmUseCase
import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortableModel, SortedModel}

object SortByInsertionSortUseCase extends SortingAlgorithmUseCase:

	def apply(
		input: SortingAlgorithmUseCaseInput
	): SortedModel = ???
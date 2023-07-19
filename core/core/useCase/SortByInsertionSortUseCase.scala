package core.useCase

import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortableModel, SortedModel}
import core.Contracts.SortingAlgorithmUseCase

object SortByInsertionSortUseCase extends SortingAlgorithmUseCase:

	def apply(
		input: SortingAlgorithmUseCaseInput
	): SortedModel = ???
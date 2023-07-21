package core.useCase

import core.Contract.SortingAlgorithmUseCase
import core.input.SortingAlgorithmUseCaseInput
import core.model.SortedModel

object SortByInsertionSortUseCase extends SortingAlgorithmUseCase:

	def apply(
		input: SortingAlgorithmUseCaseInput
	): SortedModel = ???
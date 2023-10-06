package core.useCase

import core.Contract.SortingAlgorithmUseCase
import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortedModel, SortingModel}

object SortByInsertionSortUseCase extends SortingAlgorithmUseCase[SortingModel.InsertionSort]:

	def apply(
		input: SortingAlgorithmUseCaseInput
	): SortedModel[SortingModel.InsertionSort] = ???
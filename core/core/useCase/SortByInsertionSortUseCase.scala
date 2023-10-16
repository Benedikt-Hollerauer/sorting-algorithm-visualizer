package core.useCase

import core.Contract.SortingAlgorithmUseCase
import core.entity.InsertionSortEntity
import core.input.SortingAlgorithmUseCaseInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.{OrderModel, SortedModel, SortingModel}

object SortByInsertionSortUseCase extends SortingAlgorithmUseCase[SortingModel.InsertionSort]:

	def apply(
		input: SortingAlgorithmUseCaseInput
	): SortedModel[SortingModel.InsertionSort] =
		input.ordering match
			case Ascending => InsertionSortEntity.sortAscending(input.toBeSorted)
			case Descending => InsertionSortEntity.sortDescending(input.toBeSorted)
package useCase

import core.Contract.SortingAlgorithmUseCase
import core.entity.BubbleSortEntity
import core.input.SortingAlgorithmUseCaseInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.SortedModel

object SortByBubbleSortUseCase extends SortingAlgorithmUseCase:

    def apply(
        input: SortingAlgorithmUseCaseInput
    ): SortedModel =
        input.ordering match
            case Ascending => BubbleSortEntity.sortAscending(input.toBeSorted)
            case Descending => BubbleSortEntity.sortDescending(input.toBeSorted)
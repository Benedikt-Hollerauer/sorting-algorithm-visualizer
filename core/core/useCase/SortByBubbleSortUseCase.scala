package useCase

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.input.SortingAlgorithmUseCaseInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.{NonEmptyListModel, SortableModel, SortedModel}

object SortByBubbleSortUseCase:

    def apply(input: SortingAlgorithmUseCaseInput): SortedModel =
        input.ordering match
            case Ascending => BubbleSortEntity.sortAscending(input.toBeSorted)
            case Descending => BubbleSortEntity.sortDescending(input.toBeSorted)
package useCase

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.input.SortByBubbleSortInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.{NonEmptyListModel, SortableModel, SortedModel}
import error.useCaseError.SortByBubbleSortUseCaseError

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): LazyList[SortedModel] =
        input.ordering match
            case Ascending => BubbleSortEntity.sortAscendingWithIntermediateResults(input.toBeSorted)
            case Descending => BubbleSortEntity.sortDescendingWithIntermediateResults(input.toBeSorted)
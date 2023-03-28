package useCase

import core.entity.BubbleSortEntity
import core.model.OrderModel.{Ascending, Descending}
import core.model.SortableModel
import error.useCaseError.SortByBubbleSortUseCaseError
import input.SortByBubbleSortInput

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortableModel]] =
        for
            toBeSorted <- input.toBeSorted.left.map(SortByBubbleSortUseCaseError.InputFailure(_))
            sorted = input.ordering match
                case Ascending => BubbleSortEntity.sortAscendingWithIntermediateResults(toBeSorted)
                case Descending => BubbleSortEntity.sortDescendingWithIntermediateResults(toBeSorted)
        yield sorted
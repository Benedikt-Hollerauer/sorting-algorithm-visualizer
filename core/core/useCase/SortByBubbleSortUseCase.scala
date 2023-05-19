package useCase

import core.entity.BubbleSortEntity
import core.input.SortByBubbleSortInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.SortedModel
import error.useCaseError.SortByBubbleSortUseCaseError

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortedModel]] =
        for
            toBeSorted <- input.toBeSorted
                .left
                .map: 
                    SortByBubbleSortUseCaseError.InputFailure(_)
            sorted = input.ordering match
                case Ascending => BubbleSortEntity.sortAscendingWithIntermediateResults(toBeSorted)
                case Descending => BubbleSortEntity.sortDescendingWithIntermediateResults(toBeSorted)
        yield sorted
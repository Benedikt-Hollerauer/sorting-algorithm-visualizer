package useCase

import core.entity.BubbleSortEntity
import core.model.OrderValue.{Ascending, Descending}
import core.model.SortableValue
import error.useCaseError.SortByBubbleSortUseCaseError
import input.SortByBubbleSortInput

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortableValue]] =
        for
            toBeSorted <- SortableValue.from(input.list).left.map(SortByBubbleSortUseCaseError.InputFailure(_))
            sorted = input.ordering match
                case Ascending => BubbleSortEntity.sortAscendingWithIntermediateResults(toBeSorted)
                case Descending => BubbleSortEntity.sortDescendingWithIntermediateResults(toBeSorted)
        yield sorted
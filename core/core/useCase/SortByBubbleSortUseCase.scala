package useCase

import core.Util.toValuesWithIndices
import core.entity.BubbleSortEntity
import core.input.SortByBubbleSortInput
import core.model.OrderModel.{Ascending, Descending}
import core.model.{NonEmptyListModel, SortableModel, SortedModel}
import error.useCaseError.SortByBubbleSortUseCaseError

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortedModel]] =
        for
            nonEmptyListModel <- NonEmptyListModel.from(
                input.toBeSorted.toValuesWithIndices
            ).left
                .map: nonEmptyListModelError =>
                    SortByBubbleSortUseCaseError.NonEmptyListModelCreationFailed(nonEmptyListModelError)
            toBeSorted <- SortableModel.from(
                nonEmptyListModel
            ).left
                .map: sortableModelError =>
                    SortByBubbleSortUseCaseError.SortableModelCreationFailed(sortableModelError)
            sorted = input.ordering match
                case Ascending => BubbleSortEntity.sortAscendingWithIntermediateResults(toBeSorted)
                case Descending => BubbleSortEntity.sortDescendingWithIntermediateResults(toBeSorted)
        yield sorted
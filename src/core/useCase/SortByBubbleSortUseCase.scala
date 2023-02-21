package useCase

import error.useCaseError.SortByBubbleSortUseCaseError
import input.SortByBubbleSortInput
import core.value.SortableValue

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortableValue]] = ???
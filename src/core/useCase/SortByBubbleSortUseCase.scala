package useCase

import core.value.SortableValue
import error.useCaseError.SortByBubbleSortUseCaseError
import input.SortByBubbleSortInput

object SortByBubbleSortUseCase:

    def apply(input: SortByBubbleSortInput): Either[SortByBubbleSortUseCaseError, LazyList[SortableValue]] = ???
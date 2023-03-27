package useCaseTest

import error.useCaseError.SortByBubbleSortUseCaseError
import error.modelError.SortableModelError
import mock.inputMock.SortByBubbleSortInputMock
import useCase.SortByBubbleSortUseCase

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `LazyList[Sortable](ascending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.ascendingOrder
                )
            yield assert(res.reverse.head.list == List(1, 2, 3, 4, 8, 16, 435, 5934))

        def `LazyList[Sortable](descending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            yield assert(res.reverse.head.list == List(5934, 435, 16, 8, 4, 3, 2, 1))

        def `InputFailure[EmptyList]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.emptyListFailure
                ).left
            yield assert(res == SortByBubbleSortUseCaseError.InputFailure(
                SortableModelError.EmptyList(
                    List.empty
                )
            ))

        def `InputFailure[ToFewElements]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.toFewElementsFailure
                ).left
            yield assert(res == SortByBubbleSortUseCaseError.InputFailure(
                SortableModelError.ToFewElements(
                    List(1)
                )
            ))
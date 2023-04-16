package useCaseTest

import error.modelError.SortedModelError
import error.useCaseError.SortByBubbleSortUseCaseError
import mock.ToBeSortedMock
import mock.inputMock.SortByBubbleSortInputMock
import useCase.SortByBubbleSortUseCase

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `LazyList[SortedModel](ascending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.ascendingOrder
                )
            yield
                assert(res.last.list == ToBeSortedMock.ascendingOrder.sorted)
                assert(res.length > 1)

        def `LazyList[SortedModel](descending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            yield
                assert(res.last.list == ToBeSortedMock.descendingOrder.sorted)
                assert(res.length > 1)

        def `InputFailure[EmptyList]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.emptyListFailure
                ).left
            yield assert(res == SortByBubbleSortUseCaseError.InputFailure(
                SortedModelError.EmptyList
            ))

        def `InputFailure[ToFewElements]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.toFewElementsFailure
                ).left
            yield assert(res == SortByBubbleSortUseCaseError.InputFailure(
                SortedModelError.ToFewElements(1)
            ))

        def `InputFailure[ToManyElements]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.toManyElementsFailure
                ).left
            yield assert(
                res == SortByBubbleSortUseCaseError.InputFailure(
                    SortedModelError.ToManyElements(500)
                )
            )
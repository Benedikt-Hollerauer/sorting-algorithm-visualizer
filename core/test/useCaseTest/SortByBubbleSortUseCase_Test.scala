package useCaseTest

import error.modelError.SortableModelError
import error.useCaseError.SortByBubbleSortUseCaseError
import mock.ToBeSortedMock
import mock.inputMock.SortByBubbleSortInputMock
import useCase.SortByBubbleSortUseCase

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `LazyList[Sortable](ascending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.ascendingOrder
                )
            yield
                assert(res.last.list == ToBeSortedMock.ascendingOrder.sorted)
                assert(res.length > 1)

        def `LazyList[Sortable](descending)`: Unit =
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
                SortableModelError.EmptyList
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

        def `InputFailure[ToManyElements]`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.toManyElementsFailure
                ).left
            yield assert(
                res == SortByBubbleSortUseCaseError.InputFailure(
                    SortableModelError.ToManyElements(500)
                )
            )
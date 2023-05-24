package useCaseTest

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError
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
                assert(res.last.sortableWithIndex.head.value == -500)
                assert(res.last.sortableWithIndex.last.value == 999999)
                assert(res.length > 1)

        def `LazyList[SortedModel](descending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            yield
                assert(res.last.sortableWithIndex.head.value == 999999)
                assert(res.last.sortableWithIndex.last.value == -500)
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
                SortableModelError.ToFewElements(1)
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
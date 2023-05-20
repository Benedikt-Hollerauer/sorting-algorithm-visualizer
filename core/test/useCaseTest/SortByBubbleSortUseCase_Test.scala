package useCaseTest

import core.model.ValueWithIndex
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
                assert(res.last.sortableWithIndex == List(ValueWithIndex(-500,22), ValueWithIndex(-2,0), ValueWithIndex(-1,21), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(1,3), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(76,11), ValueWithIndex(84,13), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(1234,19), ValueWithIndex(6578,18), ValueWithIndex(6587,15), ValueWithIndex(999999,1)))
                assert(res.length > 1)

        def `LazyList[SortedModel](descending)`: Unit =
            for
                res <- SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            yield
                assert(res.last.sortableWithIndex == List(ValueWithIndex(999999,1), ValueWithIndex(6587,15), ValueWithIndex(6578,18), ValueWithIndex(1234,19), ValueWithIndex(564,9), ValueWithIndex(564,20), ValueWithIndex(234,12), ValueWithIndex(234,14), ValueWithIndex(234,16), ValueWithIndex(134,8), ValueWithIndex(134,17), ValueWithIndex(123,2), ValueWithIndex(123,10), ValueWithIndex(84,13), ValueWithIndex(76,11), ValueWithIndex(6,4), ValueWithIndex(6,5), ValueWithIndex(1,3), ValueWithIndex(0,6), ValueWithIndex(0,7), ValueWithIndex(-1,21), ValueWithIndex(-2,0), ValueWithIndex(-500,22)))
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
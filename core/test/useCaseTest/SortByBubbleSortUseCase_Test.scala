package useCaseTest

import core.model.{SortedModel, ValueWithIndexModel}
import error.modelError.{NonEmptyListModelError, SortableModelError}
import error.useCaseError.SortByBubbleSortUseCaseError
import mock.ToBeSortedMock
import mock.inputMock.SortByBubbleSortInputMock
import test.TestUtil.*
import useCase.SortByBubbleSortUseCase

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `LazyList[SortedModel](ascending)`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortByBubbleSortInputMock.ascendingOrder
            )
            assertRight(res)(
                (res: LazyList[SortedModel]) => Seq(
                    res.last.sortableWithIndex.head.value == -500,
                    res.last.sortableWithIndex.last.value == 999999,
                    res.last.sortableWithIndex.head.indexModel.index == 22,
                    res.last.sortableWithIndex.last.indexModel.index == 1,
                    res.length > 1
                )
            )

        def `LazyList[SortedModel](descending)`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortByBubbleSortInputMock.descendingOrder
            )
            assertRight(res)(
                (res: LazyList[SortedModel]) => Seq(
                    res.last.sortableWithIndex.head.value == 999999,
                    res.last.sortableWithIndex.last.value == -500,
                    res.last.sortableWithIndex.head.indexModel.index == 1,
                    res.last.sortableWithIndex.last.indexModel.index == 22,
                    res.length > 1
                )
            )

        def `NonEmptyListModelCreationFailed[EmptyList]`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortByBubbleSortInputMock.emptyListFailure
            )
            assertLeft(res)(
                SortByBubbleSortUseCaseError.NonEmptyListModelCreationFailed(
                    NonEmptyListModelError.EmptyList
                )
            )

        def `NonEmptyListModelCreationFailed[ToFewElements]`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortByBubbleSortInputMock.toFewElementsFailure
            )
            assertLeft(res)(
                SortByBubbleSortUseCaseError.NonEmptyListModelCreationFailed(
                    NonEmptyListModelError.ToFewElements(1)
                )
            )

        def `SortableModelCreationFailed[ToManyElements]`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortByBubbleSortInputMock.toManyElementsFailure
            )
            assertLeft(res)(
                SortByBubbleSortUseCaseError.SortableModelCreationFailed(
                    SortableModelError.ToManyElements(501)
                )
            )
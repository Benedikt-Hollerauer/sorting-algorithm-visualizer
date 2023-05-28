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

        def `SortedModel - ascending`: Unit =
            val res = Right(
                SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.ascendingOrder
                )
            )
            assertRight(res)(
                (res: SortedModel) => Seq(
                    res.sortableModel == SortByBubbleSortInputMock.ascendingOrder.toBeSorted,
                    res.changes.last.focusedIndicesChanged == false,
                    res.changes.last.focusedIndices._1.value == 1,
                    res.changes.last.focusedIndices._2.value == 1,
                    res.changes.last.focusedIndices._1.indexModel.index == 1,
                    res.changes.last.focusedIndices._2.indexModel.index == 1,
                    res.changes.length > 1
                )
            )

        def `SortedModel - descending`: Unit =
            val res = Right(
                SortByBubbleSortUseCase(
                    input = SortByBubbleSortInputMock.descendingOrder
                )
            )
            assertRight(res)(
                (res: SortedModel) => Seq(
                    res.sortableModel == SortByBubbleSortInputMock.descendingOrder.toBeSorted,
                    res.changes.last.focusedIndicesChanged == false,
                    res.changes.last.focusedIndices._1.value == 1,
                    res.changes.last.focusedIndices._2.value == 1,
                    res.changes.last.focusedIndices._1.indexModel.index == 1,
                    res.changes.last.focusedIndices._2.indexModel.index == 1,
                    res.changes.length > 1
                )
            )
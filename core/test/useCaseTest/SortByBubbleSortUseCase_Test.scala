package useCaseTest

import core.input.SortingAlgorithmUseCaseInput
import core.model.{SortedModel, SortingModel, ValueWithIndexModel}
import error.modelError.{NonEmptyListModelError, SortableModelError}
import mock.ToBeSortedMock
import mock.inputMock.SortingAlgorithmUseCaseInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil
import test.TestUtil.*
import useCase.SortByBubbleSortUseCase

object SortByBubbleSortUseCase_Test:

    object apply_should_return:

        def `SortedModel - ascending`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortingAlgorithmUseCaseInputMock.ascendingOrder
            )
            TestUtil.testCommonBubbleSortProperties(
                res = res,
                expectedLength = 45,
                headFocusedValues = (636, 743),
                headFocusedIndicesChanged = false,
                lastFocusedValues = (5, 196),
                lastFocusedIndicesChanged = false,
                sorted = SortableModelMock.sortedAscending
            )

        def `SortedModel - descending`: Unit =
            val res = SortByBubbleSortUseCase(
                input = SortingAlgorithmUseCaseInputMock.descendingOrder
            )
            TestUtil.testCommonBubbleSortProperties(
                res = res,
                expectedLength = 45,
                headFocusedValues = (743, 636),
                headFocusedIndicesChanged = true,
                lastFocusedValues = (743, 662),
                lastFocusedIndicesChanged = false,
                sorted = SortableModelMock.sortedDescending
            )
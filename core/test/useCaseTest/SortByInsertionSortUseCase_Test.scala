package test.useCaseTest

import core.model.SortedModel
import core.useCase.SortByInsertionSortUseCase
import mock.inputMock.SortingAlgorithmUseCaseInputMock
import mock.modelMock.SortableModelMock
import test.TestUtil
import test.TestUtil.assertRight

object SortByInsertionSortUseCase_Test:

	object apply_should_return:

		def `SortedModel - ascending`: Unit =
			val res = SortByInsertionSortUseCase(
				input = SortingAlgorithmUseCaseInputMock.ascendingOrder
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (636, 743),
				lastFocusedValues = (5, 196),
				sorted = SortableModelMock.sortedAscending
			)

		def `SortedModel - descending`: Unit =
			val res = SortByInsertionSortUseCase(
				input = SortingAlgorithmUseCaseInputMock.descendingOrder
			)
			TestUtil.testCommonInsertionSortProperties(
				res = res,
				expectedLength = 45,
				headFocusedValues = (636, 743),
				lastFocusedValues = (743, 662),
				sorted = SortableModelMock.sortedDescending
			)
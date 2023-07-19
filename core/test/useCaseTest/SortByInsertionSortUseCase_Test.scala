package test.useCaseTest

import core.model.SortedModel
import core.useCase.SortByInsertionSortUseCase
import mock.inputMock.SortByInsertionSortInputMock
import test.TestUtil.assertRight

object SortByInsertionSortUseCase_Test:

	object apply_should_return:

		def `SortedModel - ascending`: Unit =
			val res = Right(
				SortByInsertionSortUseCase(
					input = SortByInsertionSortInputMock.ascendingOrder
				)
			)
			assertRight(res)(
				(res: SortedModel) => Seq(
					res.toBeSorted == SortByInsertionSortInputMock.ascendingOrder.toBeSorted,
					res.changes.head.focusedIndices._1.value == 636,
					res.changes.head.focusedIndices._2.value == 743,
					res.changes.head.focusedIndicesChanged == false,
					res.changes.last.focusedIndicesChanged == false,
					res.changes.last.focusedIndices._1.value == 5,
					res.changes.last.focusedIndices._2.value == 196,
					res.changes.last.focusedIndices._1.indexModel.index == 2,
					res.changes.last.focusedIndices._2.indexModel.index == 7,
					res.changes.length == 45
				)
			)

		def `SortedModel - descending`: Unit =
			val res = Right(
				SortByInsertionSortUseCase(
					input = SortByInsertionSortInputMock.descendingOrder
				)
			)
			assertRight(res)(
				(res: SortedModel) => Seq(
					res.toBeSorted == SortByInsertionSortInputMock.descendingOrder.toBeSorted,
					res.changes.head.focusedIndices._1.value == 743,
					res.changes.head.focusedIndices._2.value == 636,
					res.changes.head.focusedIndicesChanged == true,
					res.changes.last.focusedIndicesChanged == false,
					res.changes.last.focusedIndices._1.value == 743,
					res.changes.last.focusedIndices._2.value == 662,
					res.changes.last.focusedIndices._1.indexModel.index == 1,
					res.changes.last.focusedIndices._2.indexModel.index == 6,
					res.changes.length == 45
				)
			)
package test

import core.Util.*
import core.model.{SortingModel, ValueWithIndexModel}
import mock.ToBeSortedMock
import test.TestUtil.assertRight

object Util_Test:

	private val valuesWithIndices: List[ValueWithIndexModel] =
		ToBeSortedMock.unsorted.toValuesWithIndices

	object toValuesWithIndices_should_return:

		def `List[ValueWithIndexModel]`: Unit = // TODO use toValuesWithIndices only on NonEmptyList
			assertRight(
				Right(valuesWithIndices)
			)(
				(res: List[ValueWithIndexModel]) => Seq(
					res.head.value == ToBeSortedMock.middle,
					res.last.value == ToBeSortedMock.smallest,
					res.head.indexModel.index == 0,
					res.last.indexModel.index == 2
				)
			)

	object toValuesFromSortingModelFocusedValues_should_return:

		private val shouldBe: List[Int] = List(2, 1, 1, 2)

		def `List[Int] - SortingModel.BubbleSort`: Unit =
			val res = List(
				SortingModel.BubbleSort((valuesWithIndices.head, valuesWithIndices.last), ???, ???),
				SortingModel.BubbleSort((valuesWithIndices.last, valuesWithIndices.head), ???, ???)
			).toValuesFromSortingModelFocusedValues
			assert(res == shouldBe)

		def `List[Int] - SortingModel.InsertionSort`: Unit =
			val res = List(
				SortingModel.InsertionSort((valuesWithIndices.head, valuesWithIndices.last), ???),
				SortingModel.InsertionSort((valuesWithIndices.last, valuesWithIndices.head), ???)
			).toValuesFromSortingModelFocusedValues
			assert(res == shouldBe)
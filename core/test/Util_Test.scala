package test

import core.Util
import core.Util.{*, given}
import core.model.{SortingModel, ValueWithIndexModel}
import mock.ToBeSortedMock
import test.TestUtil.assertRight

val valuesWithIndices: List[ValueWithIndexModel] =
	ToBeSortedMock.unsorted.toValuesWithIndices

object Util_Test:

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

	object toValuesWithIndices_From_SortingModel_should_return:

		private val shouldBe: List[Int] =
			ToBeSortedMock.unsorted

		def `Some(List[ValueWithIndexModel]) - SortingModel.BubbleSort`: Unit =
			val sortingModels = List(
				SortingModel.BubbleSort((valuesWithIndices.head, valuesWithIndices(1)), List(valuesWithIndices.head), true),
				SortingModel.BubbleSort((valuesWithIndices(1), valuesWithIndices.last), List(valuesWithIndices.head), true),
			)
			val res = Util.toValuesWithIndicesFromSortingModel(sortingModels)
			assert(res.get.map(_.value) == shouldBe)

		def `Some(List[ValueWithIndexModel]) - SortingModel.InsertionSort`: Unit =
			val sortingModels = List(
				SortingModel.InsertionSort((valuesWithIndices.head, valuesWithIndices(1)), valuesWithIndices.head),
				SortingModel.InsertionSort((valuesWithIndices(1), valuesWithIndices.last), valuesWithIndices.head),
				SortingModel.InsertionSort((valuesWithIndices.head, valuesWithIndices.last), valuesWithIndices.head)
			)
			val res = Util.toValuesWithIndicesFromSortingModel(sortingModels)
			assert(res.get.map(_.value) == shouldBe)

		def `None`: Unit =
			val sortingModels = List.empty[SortingModel.BubbleSort]
			val res = Util.toValuesWithIndicesFromSortingModel(sortingModels)
			assert(res.isEmpty)
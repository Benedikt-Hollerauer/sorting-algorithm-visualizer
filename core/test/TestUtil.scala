package test

import core.model.{IndexModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel}
import mock.ToBeSortedMock
import mock.inputMock.SortingAlgorithmUseCaseInputMock
import mock.modelMock.SortableModelMock

object TestUtil:

	def assertRight[L, R](either: Either[L, R])(checkRight: R => Seq[Boolean]): Unit =
		either match
			case Left(actual) =>
				throw new IllegalArgumentException(s"Left value received, but no expected Left value was provided: $actual")
			case Right(actual) =>
				checkRight(actual).foreach: assertionResult =>
					assert(assertionResult, s"Assertion failed. Got: $actual")

	def assertLeft[L, R](either: Either[L, R])(expectedLeft: L): Unit =
		either match
			case Left(actual) =>
				assert(actual == expectedLeft, s"Expected Left value: $expectedLeft, but got: $actual")
			case Right(actual) =>
				throw new IllegalArgumentException(s"Right value received, but no function to check the Left value was provided: $actual")

	def testCommonPropertiesSortOnceBubbleSort(
		res: Option[List[SortingModel.BubbleSort]],
		focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
		focusIndicesHeadAndTail: (Int, Int),
		alreadySorted: List[ValueWithIndexModel]
	): Unit =
		val unwrappedRes = res.get
		assert:
			unwrappedRes.map(_ match
				case SortingModel.BubbleSort(focusedValues, _, _) => focusedValues._1.value
			) :+ (
				unwrappedRes.last match
					case SortingModel.BubbleSort(focusedValues, _, _) => focusedValues._2.value
			) == ToBeSortedMock.descendingOrder.sortedOnce
		assert(unwrappedRes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
		assert(unwrappedRes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
		assert(unwrappedRes.head.focusedValues._1.value == focusIndicesHeadAndTail._1)
		assert(unwrappedRes.last.focusedValues._2.value == focusIndicesHeadAndTail._2)

	def testCommonBubbleSortProperties(
		res: SortedModel,
		expectedLength: Int,
		headFocusedValues: (Int, Int),
		headFocusedIndicesChanged: Boolean,
		lastFocusedValues: (Int, Int),
		lastFocusedIndicesChanged: Boolean,
		sorted: SortableModel
	): Unit =
		assert(res.toBeSorted == SortableModelMock.unsorted)
		assert(res.changes.length == expectedLength)
		assert(res.sorted.valuesWithIndices.list.map(_.value) == sorted.valuesWithIndices.list.map(_.value))
		res.changes.head match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == headFocusedValues._1)
				assert(focusedValues._2.value == headFocusedValues._2)
				assert(focusedIndicesChanged == headFocusedIndicesChanged)
			case _ => assert(false)
		res.changes.last match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == lastFocusedValues._1)
				assert(focusedValues._2.value == lastFocusedValues._2)
				assert(focusedIndicesChanged == lastFocusedIndicesChanged)
			case _ => assert(false)

	def testCommonInsertionSortProperties(
		res: SortedModel,
		expectedLength: Int,
		headFocusedValues: (Int, Int),
		lastFocusedValues: (Int, Int),
		sorted: SortableModel
	): Unit =
		assert(res.toBeSorted == SortableModelMock.unsorted)
		assert(res.changes.length == expectedLength)
		assert(res.sorted.valuesWithIndices.list.map(_.value) == sorted.valuesWithIndices.list.map(_.value))
		res.changes.head match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == headFocusedValues._1)
				assert(focusedValues._2.value == headFocusedValues._2)
			case _ => assert(false)
		res.changes.last match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == lastFocusedValues._1)
				assert(focusedValues._2.value == lastFocusedValues._2)
			case _ => assert(false)
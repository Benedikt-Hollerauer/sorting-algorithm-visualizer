package test

import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.SortableModelMock
import core.Util

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
		alreadySorted: List[ValueWithIndexModel],
		sortedOnce: List[Int]
	): Unit =
		val unwrappedRes = res.get
		assert:
			unwrappedRes.map(_ match
				case SortingModel.BubbleSort(focusedValues, _, _) => focusedValues._1.value
			) :+ (
				unwrappedRes.last match
					case SortingModel.BubbleSort(focusedValues, _, _) => focusedValues._2.value
			) == sortedOnce
		assert(unwrappedRes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
		assert(unwrappedRes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
		assert(unwrappedRes.head.focusedValues._1.value == focusIndicesHeadAndTail._1)
		assert(unwrappedRes.last.focusedValues._2.value == focusIndicesHeadAndTail._2)

	def testCommonBubbleSortProperties(
		res: SortedModel[SortingModel.BubbleSort],
		expectedLength: Int,
		headFocusedValues: (Int, Int),
		headFocusedIndicesChanged: Boolean,
		lastFocusedValues: (Int, Int),
		lastFocusedIndicesChanged: Boolean,
		sorted: SortableModel[ValueWithIndexModel]
	): Unit =
		assert(res.toBeSorted == SortableModelMock.unsorted)
		assert(res.changes.length == expectedLength)
		assert(res.sorted.list.map(_.value) == sorted.list.map(_.value))
		res.changes.head match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == headFocusedValues._1)
				assert(focusedValues._2.value == headFocusedValues._2)
				assert(focusedIndicesChanged == headFocusedIndicesChanged)
			case null => assert(false)
		res.changes.last match
			case SortingModel.BubbleSort(focusedValues, _, focusedIndicesChanged) =>
				assert(focusedValues._1.value == lastFocusedValues._1)
				assert(focusedValues._2.value == lastFocusedValues._2)
				assert(focusedIndicesChanged == lastFocusedIndicesChanged)
			case null => assert(false)

	def testCommonInsertionSortProperties(
		res: SortedModel[SortingModel.InsertionSort], // TODO add a typeclass for the two methods
		expectedLength: Int,
		headFocusedValues: (Int, Int),
		lastFocusedValues: (Int, Int),
		sorted: SortableModel[ValueWithIndexModel]
	): Unit =
		assert(res.toBeSorted == SortableModelMock.unsorted)
		assert(res.changes.length == expectedLength)
		assert(res.sorted.list.map(_.value) == sorted.list.map(_.value))
		res.changes.head match
			case SortingModel.InsertionSort(focusedValues, currentPivot) =>
				assert(focusedValues._1.value == headFocusedValues._1)
				assert(focusedValues._2.value == headFocusedValues._2)
			case null => assert(false)
		res.changes.last match
			case SortingModel.InsertionSort(focusedValues, currentPivot) =>
				assert(focusedValues._1.value == lastFocusedValues._1)
				assert(focusedValues._2.value == lastFocusedValues._2)
			case null => assert(false)
			
	def testCommonInsertionSortSortSublistOnceProperties(
		res: List[SortingModel.InsertionSort],
		expectedLength: Int,
		headFocusedValues: (Int, Int),
		lastFocusedValues: Option[(Int, Int)],
		currentPivotValue: Int,
		shouldBeSortedSubListOnce: List[Int]
	): Unit =
		assert(res.head.focusedValues._1.value == headFocusedValues._1)
		assert(res.head.focusedValues._2.value == headFocusedValues._2)
		if(lastFocusedValues.isDefined)
			assert(res.last.focusedValues._1.value == lastFocusedValues.get._1)
			assert(res.last.focusedValues._2.value == lastFocusedValues.get._2)
		assert(res.length == expectedLength)
		assert(
			res.exists:
				case SortingModel.InsertionSort(_, currentPivot) => currentPivot.value == currentPivotValue
		)
		assert(
			Util.toValuesWithIndicesFromSortingModel(res).get == shouldBeSortedSubListOnce
		)
		
	def testCommonVisualizeEntityProperties(
		res: VisualizeModel,
		headFirstLastBarState: (BarStateModel, BarStateModel),
		lastFirstLastBarState: (BarStateModel, BarStateModel)
	): Unit = Seq(
		res.changes.head.list.head.value == ToBeSortedMock.unsorted.head,
		res.changes.head.list.last.value == ToBeSortedMock.unsorted.last,
		res.changes.last.list.head.value == ToBeSortedMock.smallest,
		res.changes.last.list.last.value == ToBeSortedMock.biggest,
		res.changes.head.list.head.barState == headFirstLastBarState._1,
		res.changes.head.list.last.barState == headFirstLastBarState._2,
		res.changes.last.list.head.barState == lastFirstLastBarState._1,
		res.changes.last.list.last.barState == lastFirstLastBarState._2,
		res.changes.head.list.length == res.changes.head.list.length,
		res.notStartedSorting.list.exists:
			case BarModel(_, barState) => barState == BarStateModel.Normal
		,
		res.finishedSorting.list.head.barState == BarStateModel.FinishedSorting,
		res.finishedSorting.list.last.barState == BarStateModel.FinishedSorting,
		res.finishedSorting.list.head.value == ToBeSortedMock.smallest,
		res.finishedSorting.list.last.value == ToBeSortedMock.biggest
	).foreach(assert(_))
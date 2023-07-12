package test

import core.model.{IndexModel, SortedModel, SortingModel, ValueWithIndexModel}
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

	def testCommonProperties(
		res: SortedModel,
		focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
		focusedIndicesFirstValueWithIndex: (Int, Int),
		focusedIndicesSecondValueWithIndex: (Int, Int),
		alreadySorted: List[ValueWithIndexModel]
	): Unit =
		assert(res.toBeSorted == SortableModelMock.unsorted)
		assert(res.changes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
		assert(res.changes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
		assert(res.changes.last.focusedIndices._1.value == focusedIndicesFirstValueWithIndex._1)
		assert(res.changes.last.focusedIndices._1.indexModel.index == focusedIndicesFirstValueWithIndex._2)
		assert(res.changes.last.focusedIndices._2.value == focusedIndicesSecondValueWithIndex._1)
		assert(res.changes.last.focusedIndices._2.indexModel.index == focusedIndicesSecondValueWithIndex._2)
		assert(res.changes.last.alreadySorted.map(_.value) == alreadySorted.map(_.value))

	def testIfEmptyValueWithIndexModelExists(res: List[SortingModel]): Unit =
		assert(
			res.exists:
				case SortingModel((ValueWithIndexModel(value0, _), ValueWithIndexModel(value1, _)), _, _) => value0 != 0 && value1 != 0
		)

	def testIfEmptyIndexModelExists(res: List[SortingModel]): Unit =
		assert(
			res.exists:
				case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _, _) => index0 != -1 && index1 != -1
		)

	def testCommonPropertiesSortOnce(
		res: Option[List[SortingModel]],
		focusedIndicesChangedHeadAndTail: (Boolean, Boolean),
		focusIndicesHeadAndTail: (Int, Int),
		alreadySorted: List[ValueWithIndexModel]
	): Unit =
		val unwrappedRes = res.get
		assert(unwrappedRes.head.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._1)
		assert(unwrappedRes.last.focusedIndicesChanged == focusedIndicesChangedHeadAndTail._2)
		assert(unwrappedRes.head.focusedIndices._1.value == focusIndicesHeadAndTail._1)
		assert(unwrappedRes.last.focusedIndices._2.value == focusIndicesHeadAndTail._2)
		testIfEmptyValueWithIndexModelExists(unwrappedRes)
		testIfEmptyIndexModelExists(unwrappedRes)
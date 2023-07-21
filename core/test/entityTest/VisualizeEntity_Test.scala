package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.VisualizeEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.{SortableModelMock, SortedModelMock}
import test.TestUtil.assertRight

object VisualizeEntity_Test:

	object getBarVisualisation_should_return:

		def `VisualizeModel`: Unit =
			val res = Right(
				VisualizeEntity.getBarVisualisation(
					sortedModel = SortedModelMock.sortedModel
				)
			)
			assertRight(res)(
				(res: VisualizeModel) =>
					Seq(
						res.changes.head.list.head.value == ToBeSortedMock.unsorted.head,
						res.changes.head.list.last.value == ToBeSortedMock.unsorted.last,
						res.changes.last.list.head.value == ToBeSortedMock.smallest,
						res.changes.last.list.last.value == ToBeSortedMock.biggest,
						res.changes.head.list.head.barState == BarStateModel.Focused,
						res.changes.head.list.last.barState == BarStateModel.Normal,
						res.changes.last.list.head.barState == BarStateModel.Focused,
						res.changes.last.list.last.barState == BarStateModel.AlreadySorted,
						res.changes.head.list.length == res.changes.head.list.length,
						res.notStartedSorting.list.exists:
							case BarModel(_, barState) => barState == BarStateModel.Normal
						,
						res.finishedSorting.list.head.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.last.barState == BarStateModel.FinishedSorting,
						res.finishedSorting.list.head.value == ToBeSortedMock.smallest,
						res.finishedSorting.list.last.value == ToBeSortedMock.biggest
					)
			)

	object getBarModel_should_return:

		private val valuesWithIndices = SortedModelMock.sortedModel.toBeSorted.list

		def `BarModel - BarStateModel.Normal`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.head,
				SortedModelMock.changes(1)
			)
			assert(res.barState == BarStateModel.Normal)

		def `BarModel - BarStateModel.Focused`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(0),
				SortedModelMock.changes(13)
			)
			assert(res.barState == BarStateModel.Focused)

		def `BarModel - BarStateModel.Swapped`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(2),
				SortedModelMock.changes(1)
			)
			assert(res.barState == BarStateModel.Swapped)

		def `BarModel - BarStateModel.AlreadySorted`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.last,
				SortedModelMock.changes.last
			)
			assert(res.barState == BarStateModel.AlreadySorted)

	object getSpecialBars_should_return:

		def `NonEmptyListModel[BarModel] - BarStateModel.Normal`: Unit =
			val res = VisualizeEntity.getSpecialBars(
				SortableModelMock.unsorted,
				BarStateModel.Normal
			)
			assert(res.list.exists:
				case BarModel(_, barState) => barState == BarStateModel.Normal
			)

		def `NonEmptyListModel[BarModel] - BarStateModel.FinishedSorting`: Unit =
			val res = VisualizeEntity.getSpecialBars(
				SortableModelMock.unsorted,
				BarStateModel.FinishedSorting
			)
			assert(res.list.exists:
				case BarModel(_, barState) => barState == BarStateModel.FinishedSorting
			)


	object swapSortable_should_return:

		def `SortableModel`: Unit =
			val firstValueWithIndex = SortableModelMock.unsorted.list(0)
			val secondValueWithIndex = SortableModelMock.unsorted.list(1)
			val res = VisualizeEntity.swapSortableValues(
				toBeUpdated = SortableModelMock.unsorted,
				swappedValues = (
					firstValueWithIndex,
					secondValueWithIndex
				)
			)
			val resList = res.list
			assert(resList(0) == secondValueWithIndex)
			assert(resList(1) == firstValueWithIndex)
			assert(resList.length == SortableModelMock.unsorted.list.length)
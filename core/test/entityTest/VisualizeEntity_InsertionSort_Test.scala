package test.entityTest

import core.entity.VisualizeEntity
import core.model.{BarStateModel, IndexModel, SortingModel, ValueWithIndexModel}
import core.typeClass.GetBarModel.{*, given}
import core.typeClass.GetBarVisualisation.{*, given}
import core.typeClass.{GetBarModel, GetBarVisualisation}
import mock.modelMock.SortedModelMock
import test.TestUtil

object VisualizeEntity_InsertionSort_Test:

	object getBarVisualisation_should_return:

		def `VisualizeModel`: Unit =
			val res = VisualizeEntity().getBarVisualisation(
				sortedModel = SortedModelMock.sortedModelInsertionSort
			)
			TestUtil.testCommonVisualizeEntityProperties(
				res = res,
				headFirstLastBarState = (BarStateModel.Focused, BarStateModel.Normal),
				lastFirstLastBarState = (BarStateModel.Focused, BarStateModel.AlreadySorted)
			)

	object getBarModel_should_return:

		private val valuesWithIndices = SortedModelMock.sortedModelInsertionSort.toBeSorted.list

		def `BarModel - BarStateModel.Normal`: Unit =
			val res = VisualizeEntity().getBarModel(
				ValueWithIndexModel(1, IndexModel.from(3).toOption.get),
				SortingModel.InsertionSort(
					focusedValues = (
						ValueWithIndexModel(5, IndexModel.from(0).toOption.get),
						ValueWithIndexModel(3, IndexModel.from(1).toOption.get)
					),
					currentPivot = ValueWithIndexModel(3, IndexModel.from(1).toOption.get)
				)
			)
			assert(res.barState == BarStateModel.Normal)

		def `BarModel - BarStateModel.Focused`: Unit =
			val res = VisualizeEntity().getBarModel(
				valuesWithIndices(0),
				SortedModelMock.changesInsertionSort(13)
			)
			assert(res.barState == BarStateModel.Focused)

		def `BarModel - BarStateModel.Swapped`: Unit =
			val res = VisualizeEntity().getBarModel(
				valuesWithIndices(2),
				SortedModelMock.changesInsertionSort(1)
			)
			assert(res.barState == BarStateModel.Swapped)

		def `BarModel - BarStateModel.AlreadySorted`: Unit =
			val res = VisualizeEntity().getBarModel(
				valuesWithIndices.last,
				SortedModelMock.changesInsertionSort.last
			)
			assert(res.barState == BarStateModel.AlreadySorted)

		def `BarModel - BarStateModel.CurrentPivot`: Unit =
			val res = VisualizeEntity().getBarModel(
				ValueWithIndexModel(3, IndexModel.from(1).toOption.get),
				SortingModel.InsertionSort(
					focusedValues = (
						ValueWithIndexModel(5, IndexModel.from(0).toOption.get),
						ValueWithIndexModel(3, IndexModel.from(1).toOption.get)
					),
					currentPivot = ValueWithIndexModel(3, IndexModel.from(1).toOption.get)
				)
			)
			assert(res.barState == BarStateModel.CurrentPivot)
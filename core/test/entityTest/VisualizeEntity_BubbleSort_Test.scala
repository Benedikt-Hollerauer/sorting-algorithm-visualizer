package test.entityTest

import core.Util.toValuesWithIndices
import core.entity.VisualizeEntity
import core.model.*
import mock.ToBeSortedMock
import mock.modelMock.{SortableModelMock, SortedModelMock}
import test.TestUtil
import test.TestUtil.assertRight
import core.typeClass.GetBarVisualisation.{*, given}
import core.typeClass.GetBarModel.{*, given}
import core.typeClass.{GetBarModel, GetBarVisualisation}

object VisualizeEntity_BubbleSort_Test:

	object getBarVisualisation_should_return:

		def `VisualizeModel`(
			using getBarVisualisation: GetBarVisualisation[SortingModel]
		)(
			using getBarModel: GetBarModel[SortingModel]
		): Unit =
			val res = VisualizeEntity.getBarVisualisation(
				sortedModel = SortedModelMock.sortedModelBubbleSort
			)
			TestUtil.testCommonVisualizeEntityProperties(
				res = res,
				headFirstLastBarState = (BarStateModel.Focused, BarStateModel.Normal),
				lastFirstLastBarState = (BarStateModel.Focused, BarStateModel.AlreadySorted)
			)

	object getBarModel_should_return:

		private val valuesWithIndices = SortedModelMock.sortedModelBubbleSort.toBeSorted.list

		def `BarModel - BarStateModel.Normal`(
			using getBarModel: GetBarModel[SortingModel]
		): Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.head,
				SortedModelMock.changesBubbleSort(1)
			)
			assert(res.barState == BarStateModel.Normal)

		def `BarModel - BarStateModel.Focused`(
			using getBarModel: GetBarModel[SortingModel]
		): Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(0),
				SortedModelMock.changesBubbleSort(13)
			)
			assert(res.barState == BarStateModel.Focused)

		def `BarModel - BarStateModel.Swapped`(
		  	using getBarModel: GetBarModel[SortingModel]
		): Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(2),
				SortedModelMock.changesBubbleSort(1)
			)
			assert(res.barState == BarStateModel.Swapped)

		def `BarModel - BarStateModel.AlreadySorted`(
			using getBarModel: GetBarModel[SortingModel]
		): Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.last,
				SortedModelMock.changesBubbleSort.last
			)
			assert(res.barState == BarStateModel.AlreadySorted)
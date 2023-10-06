package test.entityTest

import core.entity.VisualizeEntity
import core.model.{BarModel, BarStateModel, SortingModel, VisualizeModel}
import core.typeClass.GetBarModel.{*, given}
import core.typeClass.GetBarVisualisation.{*, given}
import core.typeClass.{GetBarModel, GetBarVisualisation}
import mock.ToBeSortedMock
import mock.modelMock.{SortableModelMock, SortedModelMock}
import test.TestUtil

import scala.collection.immutable.Seq

object VisualizeEntity_InsertionSort_Test:

	object getBarVisualisation_should_return:

		def `VisualizeModel`: Unit =
			val res = summon[VisualizeEntity[SortingModel.InsertionSort]].getBarVisualisation(
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
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.head,
				SortedModelMock.changesInsertionSort(1)
			)
			assert(res.barState == BarStateModel.Normal)

		def `BarModel - BarStateModel.Focused`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(0),
				SortedModelMock.changesInsertionSort(13)
			)
			assert(res.barState == BarStateModel.Focused)

		def `BarModel - BarStateModel.Swapped`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices(2),
				SortedModelMock.changesInsertionSort(1)
			)
			assert(res.barState == BarStateModel.Swapped)

		def `BarModel - BarStateModel.AlreadySorted`: Unit =
			val res = VisualizeEntity.getBarModel(
				valuesWithIndices.last,
				SortedModelMock.changesInsertionSort.last
			)
			assert(res.barState == BarStateModel.AlreadySorted)
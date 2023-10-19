package core

import core.model.{IndexModel, SortingModel, ValueWithIndexModel}

object Util:

	def toValuesWithIndicesFromSortingModel(
		sortingModels: List[SortingModel]
	): List[ValueWithIndexModel] =
		sortingModels.map(_.getFocusedValues._1) :+ sortingModels.last.getFocusedValues._2

	extension (list: List[Int])
		def toValuesWithIndices: List[ValueWithIndexModel] = list
			.zipWithIndex
			.map: (value, index) =>
				ValueWithIndexModel(
					value = value,
					indexModel = IndexModel.fromUnsafe(
						mayBeIndex = index
					)
				)
package core

import core.model.{IndexModel, SortingModel, ValueWithIndexModel}

import scala.util.Try

object Util:

	def toValuesWithIndicesFromSortingModel(
		sortingModels: List[SortingModel]
	): Option[List[ValueWithIndexModel]] =
		Try(
			sortingModels.map(_.getFocusedValues._1) :+ sortingModels.last.getFocusedValues._2
		).toOption

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
package core

import core.model.{IndexModel, SortingModel, ValueWithIndexModel}

import scala.util.Try

object Util:

	def toValuesWithIndicesFromSortingModel(
		sortingModels: List[SortingModel]
	): Option[List[ValueWithIndexModel]] =
		Try(
			sortingModels.head match //TODO if every sorting model needs its own algorithm -> type classes
				case SortingModel.BubbleSort((_, _), _, _) =>
					sortingModels.map(_.getFocusedValues._1) :+ sortingModels.last.getFocusedValues._2
				case SortingModel.InsertionSort((_, _), _) =>
					(sortingModels.head.getFocusedValues._1 +: sortingModels.tail.map(_.getFocusedValues._2))
						.reverse
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
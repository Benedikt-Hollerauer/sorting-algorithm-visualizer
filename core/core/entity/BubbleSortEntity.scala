package core.entity

import core.SortingAlgorithm
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

object BubbleSortEntity extends SortingAlgorithm:

	def sortAscending(sortable: SortableModel): SortedModel =
		val res = sortable.valuesWithIndices
			.list
			.foldLeft(
				(LazyList.empty[SortingModel], 0)
			): (acc, _) =>
				acc._1.lastOption match
					case Some(last) => (
						acc._1 ++ sortable
							.valuesWithIndices
							.list
							.drop(acc._2)
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if (first.focusedIndices._2.value <= second.value) SortingModel(
									focusedIndices = (
										first.focusedIndices._2,
										second
									),
									focusedIndicesChanged = false
								)
								else SortingModel(
									focusedIndices = (
										second,
										first.focusedIndices._2
									),
									focusedIndicesChanged = true
								),
						acc._2 + 1
					)
					case None => (
						acc._1 ++ sortable
							.valuesWithIndices
							.list
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if (first.focusedIndices._2.value >= second.value) SortingModel(
									focusedIndices = (
										first.focusedIndices._2,
										second
									),
									focusedIndicesChanged = false
								)
								else SortingModel(
									focusedIndices = (
										second,
										first.focusedIndices._2
									),
									focusedIndicesChanged = true
								),
						acc._2 + 1
					)
			._1.filter:
				case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		res.foreach(println)
		SortedModel(
			sortableModel = sortable,
			changes = res
		)

	def sortDescending(sortable: SortableModel): SortedModel =
		val res = sortable.valuesWithIndices
			.list
			.foldLeft(
				(LazyList.empty[SortingModel], 0)
			): (acc, _) =>
				acc._1.lastOption match
					case Some(last) => (
						acc._1 ++ sortable
							.valuesWithIndices
							.list
							.drop(acc._2)
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if (first.focusedIndices._2.value <= second.value) SortingModel(
									focusedIndices = (
										first.focusedIndices._2,
										second
									),
									focusedIndicesChanged = false
								)
								else SortingModel(
									focusedIndices = (
										second,
										first.focusedIndices._2
									),
									focusedIndicesChanged = true
								),
						acc._2 + 1
					)
					case None => (
						acc._1 ++ sortable
							.valuesWithIndices
							.list
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if (first.focusedIndices._2.value >= second.value) SortingModel(
									focusedIndices = (
										first.focusedIndices._2,
										second
									),
									focusedIndicesChanged = false
								)
								else SortingModel(
									focusedIndices = (
										second,
										first.focusedIndices._2
									),
									focusedIndicesChanged = true
								),
						acc._2 + 1
					)
			._1.filter:
				case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		SortedModel(
			sortableModel = sortable,
			changes = res
		)
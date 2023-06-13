package core.entity

import core.SortingAlgorithm
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

object BubbleSortEntity extends SortingAlgorithm:

	override def sortAscending(sortable: SortableModel): SortedModel =
		sort(sortable, _ <= _)

	override def sortDescending(sortable: SortableModel): SortedModel =
		sort(sortable, _ >= _)

	private def sort(sortable: SortableModel, comparator: (Int, Int) => Boolean): SortedModel =
		def bubbleSort(
			toBeCompared: List[ValueWithIndexModel],
			acc: List[SortingModel] = List.empty[SortingModel],
			comparator: (Int, Int) => Boolean
		): List[SortingModel] =
			toBeCompared match
				case first :: second :: tail if comparator(first.value, second.value) =>
					bubbleSort(
						toBeCompared = second :: tail,
						acc = acc :+ SortingModel(
							focusedIndices = (first, second),
							focusedIndicesChanged = false
						),
						comparator = comparator
					)
				case first :: second :: tail =>
					bubbleSort(
						toBeCompared = second :: tail,
						acc = acc :+ SortingModel(
							focusedIndices = (second, first),
							focusedIndicesChanged = true
						),
						comparator = comparator
					)
				case first :: Nil => acc :+ SortingModel(
					focusedIndices = (acc.last.focusedIndices._2, first),
					focusedIndicesChanged = true
				)
				//TODO: probably add another comparison here
				case Nil => acc

		val res = sortable.valuesWithIndices
			.list
			.foldLeft(
				(LazyList.empty[SortingModel], sortable.valuesWithIndices.list)
			): (acc, _) =>
				acc._1.lastOption match
					case Some(last) => (
						acc._1 ++ acc._2
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if(comparator(first.focusedIndices._2.value, second.value)) SortingModel(
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
						acc._2.drop(1)
					)
					case None => (
						acc._1 ++ acc._2
							.scanLeft(
								SortingModel.empty
							): (first, second) =>
								if(comparator(first.focusedIndices._2.value, second.value)) SortingModel(
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
						acc._2
					)
			._1.filter:
				case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		SortedModel(
			sortableModel = sortable,
			changes = res
		)
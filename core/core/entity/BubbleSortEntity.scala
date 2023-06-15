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

	def sortOnce(
		toBeCompared: List[ValueWithIndexModel],
		comparator: OrderModel,
	): List[SortingModel] =
		toBeCompared.scanLeft(SortingModel.empty): (first, second) =>
			if(first.focusedIndices._1 == ValueWithIndexModel.empty)
				SortingModel(
					focusedIndices = (second, first.focusedIndices._1),
					focusedIndicesChanged = false
				)
			else if(first.focusedIndices._2 == ValueWithIndexModel.empty) //TODO: I think i have to add a comparator.getOrdering comparison, to check how the should be ordered
				SortingModel(
					focusedIndices = (first.focusedIndices._1, second),
					focusedIndicesChanged = false
				)
			else if(comparator.getOrdering(first.focusedIndices._2.value, second.value))
				SortingModel(
					focusedIndices = (first.focusedIndices._2, second),
					focusedIndicesChanged = false
				)
			else SortingModel(
				focusedIndices = (second, first.focusedIndices._2),
				focusedIndicesChanged = true
			)
		.filter:
			case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) => index0 != -1 && index1 != -1
		val pairs = toBeCompared.zip(toBeCompared.tail)
		println(toBeCompared.map(_.value).sliding(2).toList.map(x => (x.head, x.last)))
		pairs.foldLeft(List.empty[SortingModel]):
			case (acc, (first, second)) =>
				val newModel = if (comparator.getOrdering(first.value, second.value))
					SortingModel((first, second), focusedIndicesChanged = false)
				else
					SortingModel((second, first), focusedIndicesChanged = true)
				acc :+ newModel
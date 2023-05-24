package core.entity

import core.model.OrderModel.{Ascending, Descending}
import core.model.{OrderModel, SortableModel, SortedModel, ValueWithIndexModel}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Descending)

	def sortWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted.valuesWithIndices)
			.foldLeft(
				LazyList.empty[SortedModel]
			): (acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last.sortableWithIndex, ordering)
					case None => sortOnceWithIntermediateResults(toBeSorted.valuesWithIndices, ordering) // ToDo return a error if this fails

	def sortOnceWithIntermediateResults(toBeSorted: List[ValueWithIndexModel], ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted)
			.scanLeft(
				SortedModel.from(
					List.empty[ValueWithIndexModel],
					List(0),
					false
				).toOption.get
			): (acc, next) =>
				swapByOrdering(acc, next, ordering)
			.map: sortedModel =>
				SortedModel.from(
					sortedModel.sortableWithIndex
						.++(toBeSorted.drop(sortedModel.sortableWithIndex.length)),
					sortedModel.focusedIndices,
					sortedModel.focusedIndicesChanged
				).toOption.get

	def swapByOrdering(acc: SortedModel, next: ValueWithIndexModel, ordering: OrderModel): SortedModel =
		def swapNeeded(last: ValueWithIndexModel): Boolean = ordering match
			case Ascending => last.value > next.value
			case Descending => last.value < next.value

		acc.sortableWithIndex.lastOption match
			case Some(last) if swapNeeded(last) =>
				SortedModel.from(
					(acc.sortableWithIndex.dropRight(1) :+ next) :+ last,
					List(last.index, next.index),
					true
				).toOption.get
			case Some(last) =>
				SortedModel.from(
					acc.sortableWithIndex :+ next,
					List(last.index, next.index),
					false
				).toOption.get
			case _ =>
				SortedModel.from(
					acc.sortableWithIndex :+ next,
					List(next.index),
					false
				).toOption.get
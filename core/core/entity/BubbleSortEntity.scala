package core.entity

import core.model.OrderModel.{Ascending, Descending}
import core.model.{OrderModel, SortableModel, SortedModel, ValueWithIndex}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Descending)

	def sortWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted.list)
			.foldLeft(LazyList.empty[SortedModel])((acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last.sortableWithIndex, ordering)
					case None => sortOnceWithIntermediateResults(toBeSorted.list.zipWithIndex.map((value, index) => ValueWithIndex(value, index)), ordering)
			)

	def sortOnceWithIntermediateResults(toBeSorted: List[ValueWithIndex], ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted)
			.scanLeft(
				List.empty[ValueWithIndex]
			): (acc, next) =>
				sortOnceByOrdering(acc, next, ordering)
			.map: lists =>
				lists ++ toBeSorted
					.drop(lists.length)
			.map: lists =>
				SortedModel.from(
					sortable = lists,
					mayBeFocusedIndices = List(0, 1), // ToDo: add dynamic values
					focusedIndicesChanged = true // ToDo: add dynamic values
				).toOption.get

	def sortOnceByOrdering(acc: List[ValueWithIndex], next: ValueWithIndex, ordering: OrderModel): List[ValueWithIndex] =
		ordering match
			case Ascending =>
				acc.lastOption match
					case Some(last) if last.value > next.value =>
						(acc.dropRight(1) :+ next) :+ last
					case _ =>
						acc :+ next
			case Descending =>
				acc.lastOption match
					case Some(last) if last.value < next.value =>
						(acc.dropRight(1) :+ next) :+ last
					case _ =>
						acc :+ next
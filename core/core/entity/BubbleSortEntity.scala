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
					case None => sortOnceWithIntermediateResults(toBeSorted.list.zipWithIndex.map((value, index) => ValueWithIndex.from(value, index).toOption.get), ordering) // ToDo return a error if this fails
			)

	def sortOnceWithIntermediateResults(toBeSorted: List[ValueWithIndex], ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted)
			.scanLeft(
				Test(
					List.empty[ValueWithIndex],
					List(0),
					false
				)
			): (acc, next) =>
				swapByOrdering(acc, next, ordering)
			.map: lists =>
				lists.copy(
					valuesWithIndices = lists.valuesWithIndices
						.++(toBeSorted.drop(lists.valuesWithIndices.length))
				)
			.map: valuesWithIndices =>
				SortedModel.from(
					sortable = valuesWithIndices.valuesWithIndices,
					mayBeFocusedIndices = valuesWithIndices.focusedIndices,
					focusedIndicesChanged = valuesWithIndices.focusedIndicesChanged
				).toOption.get

	def swapByOrdering(acc: Test, next: ValueWithIndex, ordering: OrderModel): Test =
		def swapNeeded(last: ValueWithIndex): Boolean = ordering match
			case Ascending => last.value > next.value
			case Descending => last.value < next.value

		acc.valuesWithIndices.lastOption match
			case Some(last) if swapNeeded(last) =>
				Test(
					(acc.valuesWithIndices.dropRight(1) :+ next) :+ last,
					List(last.index, next.index),
					true
				)
			case Some(last) =>
				Test(
					acc.valuesWithIndices :+ next,
					List(last.index, next.index),
					false
				)
			case _ =>
				Test(
					acc.valuesWithIndices :+ next,
					List(next.index),
					false
				)

case class Test(
	valuesWithIndices: List[ValueWithIndex],
	focusedIndices: List[Int],
	focusedIndicesChanged: Boolean
)
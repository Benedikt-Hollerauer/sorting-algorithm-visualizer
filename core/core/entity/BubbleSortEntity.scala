package core.entity

import core.model.OrderModel.{Ascending, Descending}
import core.model.{OrderModel, SortableModel, SortedModel}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Descending)

	def sortWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted.list)
			.foldLeft(LazyList.empty[SortedModel])((acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last.sortable, ordering)
					case None => sortOnceWithIntermediateResults(toBeSorted, ordering)
			)

	def sortOnceWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted.list)
			.scanLeft(
				List.empty[Int]
			): (acc, next) =>
				sortOnceByOrdering(acc, next, ordering)
			.map: lists =>
				lists ++ toBeSorted.list
					.drop(lists.length)
			.map: lists =>
				SortedModel.from(
					sortable = SortableModel.from(lists).toOption.get,
				).toOption.get

	def sortOnceByOrdering(acc: List[Int], next: Int, ordering: OrderModel): List[Int] =
		ordering match
			case Ascending =>
				acc.lastOption match
					case Some(last) if last > next =>
						(acc.dropRight(1) :+ next) :+ last
					case _ =>
						acc :+ next
			case Descending =>
				acc.lastOption match
					case Some(last) if last < next =>
						(acc.dropRight(1) :+ next) :+ last
					case _ =>
						acc :+ next
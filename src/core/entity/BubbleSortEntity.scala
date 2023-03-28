package core.entity

import core.model.OrderModel.{Ascending, Descending}
import core.model.{OrderModel, SortableModel}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortableModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortableModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Descending)

	def sortWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortableModel] =
		LazyList.from(toBeSorted.list)
			.foldLeft(LazyList.empty[SortableModel])((acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last, ordering)
					case None => sortOnceWithIntermediateResults(toBeSorted, ordering)
			)

	def sortOnceWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortableModel] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, next) =>
				ordering match
					case Ascending =>
						acc.lastOption match
							case Some(last) if last > next => (acc.dropRight(1) :+ next) :+ last
							case _ => acc :+ next
					case Descending =>
						acc.lastOption match
							case Some(last) if last < next => (acc.dropRight(1) :+ next) :+ last
							case _ => acc :+ next
			).map(list =>
				list ++ toBeSorted.list
					.drop(list.length)
			).map(SortableModel.from(_).toOption.get)
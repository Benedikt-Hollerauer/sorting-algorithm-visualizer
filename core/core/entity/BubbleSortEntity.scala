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
		val itermediate = toBeSorted.list // TODO this will not continue from here on
			.zipWithIndex
			.scanLeft(
				(List.empty[Int], List.empty[Int])
			)((acc, next) =>
				ordering match
					case Ascending =>
						acc._1.lastOption match
							case Some(last) if last > next._1 => (
								(acc._1.dropRight(1) :+ next._1) :+ last,
								acc._2 :+ next._2 // TODO this does not work as intended
							)
							case _ => (
								acc._1 :+ next._1,
								acc._2 // TODO this does not work as intended
							)
					case Descending =>
						acc._1.lastOption match
							case Some(last) if last < next._1 => (
								(acc._1.dropRight(1) :+ next._1) :+ last,
								acc._2 :+ next._2
							)
							case _ => (
								acc._1 :+ next._1,
								acc._2
							)
			)
		//println(itermediate)
		val moin = itermediate.map(lists => (
			lists._1 ++ toBeSorted.list
				.drop(lists._1.length),
			lists._2
		))
		println(moin.last)
		moin.map(lists =>
			//println(lists)
			SortedModel.from(
				sortable = SortableModel.from(lists._1).toOption.get,
				mayBeChangedIndices = lists._2
			).toOption.get //TODO the error is throwen here because list._2 has no content
		).to(LazyList)
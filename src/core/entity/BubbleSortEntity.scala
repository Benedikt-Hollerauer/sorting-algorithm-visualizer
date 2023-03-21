package core.entity

import core.value.SortableValue

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.zipWithIndex
			.scanLeft(toBeSorted.list)((acc, s) =>
				if(!acc.isDefinedAt(s._2 + 1)) acc
				else if(acc(s._2 + 1) >= s._1) acc
				else acc.updated(s._2, acc(s._2 + 1)).updated(s._2 + 1, acc(s._2))
			).map(x => SortableValue.from(x).toOption.get)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, s) =>
				acc.lastOption match
					case Some(f) =>
						if(f > s) (acc.dropRight(1) :+ s) :+ f
						else acc :+ s
					case None => acc :+ s
			).foreach(println)

		LazyList(SortableValue.from(List(1, 2, 3)).toOption.get)
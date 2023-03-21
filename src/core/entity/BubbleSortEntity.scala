package core.entity

import core.value.SortableValue

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, s) =>
				acc.lastOption match
					case Some(f) =>
						if (f > s) (acc.dropRight(1) :+ s) :+ f
						else acc :+ s
					case None => acc :+ s
			).map(list => list :: toBeSorted.list.map(x => List(x)).drop(list.length)).map(_.flatten).map(x => SortableValue.from(x).toOption.get)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, s) =>
				acc.lastOption match
					case Some(f) =>
						if(f > s) (acc.dropRight(1) :+ s) :+ f
						else acc :+ s
					case None => acc :+ s
			).map(list => list :: toBeSorted.list.map(x => List(x)).drop(list.length)).map(_.flatten).map(x => SortableValue.from(x).toOption.get)
package core.entity

import core.value.SortableValue

import java.util.Collections
import scala.util.Try

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.zipWithIndex
			.scanLeft(toBeSorted.list)((acc, s) =>
				if(Try(acc(s._2 + 1)).isFailure) acc
				else if(acc(s._2 + 1) >= s._1) acc
				else acc.updated(s._2, acc(s._2 + 1)).updated(s._2 + 1, acc(s._2))
			).map(x => SortableValue.from(x).toOption.get)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		val res = LazyList.from(toBeSorted.list)
			.zipWithIndex
			.scanLeft(toBeSorted.list)((acc, s) =>
				if(Try(acc(s._2 + 1)).isFailure) acc
				else if(acc(s._2 + 1) >= s._1) acc
				else acc.updated(s._2, acc(s._2 + 1)).updated(s._2 + 1, acc(s._2))
			).map(x => SortableValue.from(x).toOption.get)
		res.foreach(println)
		res
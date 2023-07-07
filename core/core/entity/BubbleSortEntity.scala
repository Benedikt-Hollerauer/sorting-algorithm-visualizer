package core.entity

import core.SortingAlgorithm
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

import scala.annotation.tailrec
import scala.util.Try

object BubbleSortEntity extends SortingAlgorithm:

	override def sortAscending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Ascending)

	override def sortDescending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Descending)

	//@tailrec
	private def sort(
		valuesWithIndices: List[ValueWithIndexModel],
		sortable: SortableModel,
		comparator: OrderModel,
		acc: LazyList[SortingModel] = LazyList.empty[SortingModel],
		firstIteration: Boolean = true
	): SortedModel =
		//val test = valuesWithIndices.foldLeft((
		//	LazyList.empty[SortingModel],
		//	valuesWithIndices
		//)): (acc, _) =>
		//	val sortedOnce = sortOnce(acc._2, comparator).getOrElse(acc._1.toList)
		//	(
		//		acc._1 ++ sortedOnce,
		//		sortedOnce.map(x => x.focusedIndices._1) :+ sortedOnce.last.focusedIndices._2 //TODO refactor this to be cleaner
		//	)
		//SortedModel(
		//	sortableModel = sortable,
		//	changes = test._1
		//)
		valuesWithIndices match
			case Nil => SortedModel(
				sortable,
				acc
			)
			case valuesWithIndices =>
				val sortedOnce = sortOnce(valuesWithIndices, comparator)
				val newAcc = sortedOnce match
					case Some(it) => acc ++ it
					case None => acc
				val newValuesWithIndices = sortedOnce match
					case Some(it) =>
						println(it)
						val newIt = it.map(_.focusedIndices._1) :+ it.last.focusedIndices._2
						comparator match
							case OrderModel.Ascending => newIt.filterNot(_ == valuesWithIndices.max)
							case OrderModel.Descending => newIt.filterNot(_ == valuesWithIndices.min)
					case None => Nil
				sort(
					newValuesWithIndices,
					sortable,
					comparator,
					newAcc,
					false
				)
			//case valuesWithIndices =>
			//	val newValuesWithIndices =
			//		if(firstIteration) valuesWithIndices
			//		else comparator match
			//			case OrderModel.Ascending => valuesWithIndices.filterNot(_ == valuesWithIndices.max)
			//			case OrderModel.Descending => valuesWithIndices.filterNot(_ == valuesWithIndices.min)
			//	val sortedOnce = sortOnce(newValuesWithIndices, comparator)
			//	val newAcc = sortedOnce match //TODO this has to go into new valuesWithIndices
			//		case Some(it) => acc ++ it
			//		case None => acc
			//	sort(
			//		sortedOnce match
			//			case Some(it) => it.map(_.focusedIndices._1) :+ it.last.focusedIndices._2
			//			case None => Nil
			//		,
			//		sortable,
			//		comparator,
			//		newAcc,
			//		false
			//	)

	def sortOnce(
		toBeCompared: List[ValueWithIndexModel],
		comparator: OrderModel,
	): Option[List[SortingModel]] =
		Try(
			toBeCompared.tail
				.foldLeft(
					(List.empty[SortingModel], toBeCompared.head)
				):
					case ((acc, f), s) if comparator.getOrdering(f.value, s.value) =>
						(acc :+ SortingModel((f, s), false), s)
					case ((acc, f), s) => (acc :+ SortingModel((s, f), true), f)
				._1
		).toOption
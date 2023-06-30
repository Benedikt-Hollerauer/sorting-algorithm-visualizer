package core.entity

import core.model.{BarColorModel, BarModel, NonEmptyListModel, SortableModel, SortedModel, ValueWithIndexModel}

object VisualizeEntity:

	def getBarVisualisation(
		sortedModel: SortedModel
	): LazyList[NonEmptyListModel[BarModel]] =
		sortedModel.changes.foreach(println)
		sortedModel.changes
			.foldLeft(
				(sortedModel.sortableModel, List.empty[NonEmptyListModel[BarModel]])
			): (acc, change) =>
				val newSortable =
					if(change.focusedIndicesChanged) swapSortable(acc._1, (change.focusedIndices._1, change.focusedIndices._2))
					else acc._1
				(
					newSortable,
					acc._2 :+ NonEmptyListModel.from(
						newSortable.valuesWithIndices
							.list
							.map: valueWithIndex =>
								if (valueWithIndex == change.focusedIndices._1 | valueWithIndex == change.focusedIndices._2)
									BarModel(valueWithIndex.value, BarColorModel.TTT)
								else BarModel(valueWithIndex.value, BarColorModel.`___`)
					).toOption.get
				)
			._2
			.to(LazyList)

	def swapSortable(
		toBeUpdated: SortableModel,
		swappedValues: (ValueWithIndexModel, ValueWithIndexModel)
	): SortableModel =
		val list = toBeUpdated.valuesWithIndices.list
		val swapped = list.updated(
			list.indexWhere(_ == swappedValues._1), swappedValues._2
		).updated(
			list.indexWhere(_ == swappedValues._2), swappedValues._1
		)
		SortableModel.from(
			NonEmptyListModel.from(
				swapped
			).toOption.get
		).toOption.get
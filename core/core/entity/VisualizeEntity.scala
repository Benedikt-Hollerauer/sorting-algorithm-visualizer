package core.entity

import core.model.{BarColorModel, BarModel, NonEmptyListModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel}

object VisualizeEntity:

	def getBarVisualisation(
		sortedModel: SortedModel
	): LazyList[NonEmptyListModel[BarModel]] =
		sortedModel.changes
			.foldLeft(
				(sortedModel.sortableModel, LazyList.empty[NonEmptyListModel[BarModel]])
			): (acc, change) =>
				val newSortable =
					if(change.focusedIndicesChanged) swapSortableValues(acc._1, (change.focusedIndices._1, change.focusedIndices._2))
					else acc._1
				(
					newSortable,
					acc._2 :+ NonEmptyListModel.from(
						newSortable.valuesWithIndices
							.list
							.map: valueWithIndex =>
								getBar(valueWithIndex, change)
					).toOption.get
				)
			._2

	private def getBar(valueWithIndex: ValueWithIndexModel, change: SortingModel): BarModel =
		val isCorrectValueWithIndex = valueWithIndex == change.focusedIndices._1 | valueWithIndex == change.focusedIndices._2
		if(isCorrectValueWithIndex && change.focusedIndicesChanged)
			BarModel(valueWithIndex.indexModel, valueWithIndex.value, BarColorModel.Focused)
		else if(isCorrectValueWithIndex)
			BarModel(valueWithIndex.indexModel, valueWithIndex.value, BarColorModel.Swapped)
		else BarModel(valueWithIndex.indexModel, valueWithIndex.value, BarColorModel.Normal)

	def swapSortableValues(
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
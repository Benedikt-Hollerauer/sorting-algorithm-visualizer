package core.typeClass

import core.entity.VisualizeEntity
import core.model.{BarModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel, VisualizeModel}

trait GetBarVisualisation[T]:

	def apply(
		acc: (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]]),
		change: T,
		swapSortableValues: (SortableModel[ValueWithIndexModel], (ValueWithIndexModel, ValueWithIndexModel)) => SortableModel[ValueWithIndexModel],
		getBarModel: (ValueWithIndexModel, T) => BarModel
	): (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]])
	
object GetBarVisualisation:

	given GetBarVisualisation[SortingModel.BubbleSort] with
		override def apply(
			acc: (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]]),
			change: SortingModel.BubbleSort,
			swapSortableValues: (SortableModel[ValueWithIndexModel], (ValueWithIndexModel, ValueWithIndexModel)) => SortableModel[ValueWithIndexModel],
			getBarModel: (ValueWithIndexModel, SortingModel.BubbleSort) => BarModel
		): (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]]) =
			val newSortable =
				if(change.focusedIndicesChanged) swapSortableValues(acc._1, (change.focusedValues._1, change.focusedValues._2))
				else acc._1
			(
				newSortable,
				acc._2 :+ SortableModel.fromUnsafe(
					newSortable.list
						.map: valueWithIndex =>
							getBarModel(valueWithIndex, change)
				)
			)

	given GetBarVisualisation[SortingModel.InsertionSort] with
		override def apply(
			acc: (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]]),
			change: SortingModel.InsertionSort,
			swapSortableValues: (SortableModel[ValueWithIndexModel], (ValueWithIndexModel, ValueWithIndexModel)) => SortableModel[ValueWithIndexModel],
			getBarModel: (ValueWithIndexModel, SortingModel.InsertionSort) => BarModel
		): (SortableModel[ValueWithIndexModel], LazyList[SortableModel[BarModel]]) = ???
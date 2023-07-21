package core

import core.model.{OrderModel, SortableModel, ValueWithIndexModel}

object TypeClass:

	trait GetSorted[T]:

		def getSorted(
			toBeSorted: List[T],
			ordering: OrderModel
		): SortableModel[T]

	implicit object ValueWithIndexGetSorted extends GetSorted[ValueWithIndexModel]:

		override def getSorted(
			toBeSorted: List[ValueWithIndexModel],
			ordering: OrderModel
		): SortableModel[ValueWithIndexModel] =
			val sorted = toBeSorted.sortWith: (x, y) =>
				ordering.getOrdering(x.value, y.value)
			SortableModel.fromUnsafe(
				sorted
			)
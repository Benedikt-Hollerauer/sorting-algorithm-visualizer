package core.typeClass

import core.model.{OrderModel, SortableModel, ValueWithIndexModel}

trait GetSorted[T]:

	def getSorted(
		toBeSorted: List[T],
		ordering: OrderModel
	): SortableModel[T]

object GetSorted:

	given GetSorted[ValueWithIndexModel] with
		override def getSorted(
			toBeSorted: List[ValueWithIndexModel],
			ordering: OrderModel
		): SortableModel[ValueWithIndexModel] =
			val sorted = toBeSorted.sortWith: (x, y) =>
				ordering.getOrdering(x.value, y.value)
			SortableModel.fromUnsafe(
				sorted
			)
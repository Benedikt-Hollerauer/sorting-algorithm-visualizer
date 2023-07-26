package core.model

import error.modelError.SortableModelError

case class SortableModel[T] private(
	list: List[T]
):
	import core.typeClass.GetSorted

	def getSorted(ordering: OrderModel)
		(implicit getSorted: GetSorted[T])
	: SortableModel[T] =
		getSorted.getSorted(
			list,
			ordering
		)

object SortableModel:

	val maxAllowedElements: Int = 500

	private def hasLessThanTwoElements[T](mayBeList: List[T]): Boolean = mayBeList.length < 2

	private def hasToManyElements[T](mayBeList: List[T]): Boolean = mayBeList.length > maxAllowedElements

	def from[T](mayBeList: List[T]): Either[SortableModelError, SortableModel[T]] =
		if(hasLessThanTwoElements(mayBeList)) Left(SortableModelError.LessThanTwoElements)
		else if(hasToManyElements(mayBeList)) Left(SortableModelError.ToManyElements(mayBeList.length))
		else Right(
			SortableModel(
				list = mayBeList
			)
		)

	@throws(classOf[RuntimeException])
	def fromUnsafe[T](mayBeList: List[T]): SortableModel[T] =
		if(hasLessThanTwoElements(mayBeList)) throw new RuntimeException("less than two elements")
		else if(hasToManyElements(mayBeList)) throw new RuntimeException("to many elements")
		else SortableModel(
			list = mayBeList
		)
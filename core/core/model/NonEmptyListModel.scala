package core.model

import error.modelError.NonEmptyListModelError

case class NonEmptyListModel[T] private(
	list: List[T]
)

object NonEmptyListModel:

	private def hasLessThanTwoElements[T](mayBeList: List[T]): Boolean = mayBeList.length < 2

	def from[T](mayBeList: List[T]): Either[NonEmptyListModelError, NonEmptyListModel[T]] =
		if(hasLessThanTwoElements(mayBeList)) Left(NonEmptyListModelError.LessThanTwoElements)
		else Right(
			NonEmptyListModel(
				list = mayBeList
			)
		)

	@throws(classOf[RuntimeException])
	def fromUnsafe[T](mayBeList: List[T]): NonEmptyListModel[T] =
		if(hasLessThanTwoElements(mayBeList)) throw new RuntimeException("less than 2 elements")
		else NonEmptyListModel(
			list = mayBeList
		)
package core.model

import error.modelError.NonEmptyListModelError

case class NonEmptyListModel[T] private(
	list: List[T]
)

object NonEmptyListModel:

	def from[T](mayBeList: List[T]): Either[NonEmptyListModelError, NonEmptyListModel[T]] =
		if(mayBeList.length < 2) Left(NonEmptyListModelError.LessThanTwoElements)
		else Right(
			NonEmptyListModel(
				list = mayBeList
			)
		)

	@throws(classOf[RuntimeException])
	def fromUnsafe[T](mayBeList: List[T]): NonEmptyListModel[T] =
		if(mayBeList.length < 2) throw new RuntimeException("less than 2 elements")
		else NonEmptyListModel(
			list = mayBeList
		)
package core.model

import error.modelError.NonEmptyListModelError

case class NonEmptyListModel[T] private(
	list: List[T]
)

object NonEmptyListModel:

	def from[T](mayBeList: List[T]): Either[NonEmptyListModelError, NonEmptyListModel[T]] =
		if(mayBeList.length < 2) Left(NonEmptyListModelError.ToFewElements)
		else Right(
			NonEmptyListModel(
				list = mayBeList
			)
		)
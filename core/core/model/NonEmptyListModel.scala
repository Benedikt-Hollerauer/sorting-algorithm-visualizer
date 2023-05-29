package core.model

import error.modelError.NonEmptyListModelError

case class NonEmptyListModel[T] private(
	list: List[T]
)

object NonEmptyListModel:

	def from[T](mayBeList: List[T]): Either[NonEmptyListModelError, NonEmptyListModel[T]] =
		if(mayBeList.isEmpty) Left(NonEmptyListModelError.EmptyList)
		else Right(
			NonEmptyListModel(
				list = mayBeList
			)
		)
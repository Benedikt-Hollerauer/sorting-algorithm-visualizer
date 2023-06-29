package core.model

import error.modelError.IndexModelError

case class IndexModel private(
	index: Int
)

object IndexModel:
		
	def from(mayBeIndex: Int): Either[IndexModelError, IndexModel] =
		if(mayBeIndex < 0)
			Left(
				IndexModelError.NegativeIndex(mayBeIndex)
			)
		else Right(
			IndexModel(mayBeIndex)
		)
		
	def empty: IndexModel =
		IndexModel(
			index = -1
		)
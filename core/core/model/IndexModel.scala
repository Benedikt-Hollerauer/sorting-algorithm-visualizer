package core.model

import error.modelError.IndexModelError

case class IndexModel private(
	index: Int
)

object IndexModel:
	
	private def isNegativeIndex(mayBeIndex: Int): Boolean = mayBeIndex < 0
		
	def from(mayBeIndex: Int): Either[IndexModelError, IndexModel] =
		if(isNegativeIndex(mayBeIndex)) Left(
			IndexModelError.NegativeIndex(mayBeIndex)
		)
		else Right(
			IndexModel(mayBeIndex)
		)

	@throws(classOf[RuntimeException])
	def fromUnsafe(mayBeIndex: Int): IndexModel =
		if(isNegativeIndex(mayBeIndex)) throw new RuntimeException("negative index")
		else IndexModel(
			index = mayBeIndex
		)
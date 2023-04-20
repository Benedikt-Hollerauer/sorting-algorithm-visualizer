package core.model

import error.modelError.SortedModelError

case class SortedModel(
	sortable: SortableModel,
	changedIndices: List[Int]
)

object SortedModel:

	def from(
		sortable: SortableModel,
		mayBeChangedIndices: List[Int]
	): Either[SortedModelError, SortedModel] = ???


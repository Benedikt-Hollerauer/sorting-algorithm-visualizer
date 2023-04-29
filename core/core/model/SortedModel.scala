package core.model

import error.modelError.SortedModelError

case class SortedModel private(
	sortable: SortableModel,
	changedIndices: List[Int]
)

object SortedModel:

	def from(
		sortable: SortableModel,
		mayBeChangedIndices: List[Int]
	): Either[SortedModelError, SortedModel] =
		if(mayBeChangedIndices.length <= 0) Left(SortedModelError.ToFewChangedIndices(mayBeChangedIndices))
		else if(mayBeChangedIndices.exists(_ < 0)) Left(SortedModelError.NegativeChangedIndices(mayBeChangedIndices))
		else Right(
			SortedModel(
				sortable = sortable,
				changedIndices = mayBeChangedIndices
			)
		)


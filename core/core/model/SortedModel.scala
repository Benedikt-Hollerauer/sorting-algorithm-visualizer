package core.model

import error.modelError.SortedModelError

case class SortedModel private(
	sortable: SortableModel,
	focusedIndices: List[Int],
	focusedIndicesChanged: Boolean
)

object SortedModel:

	def from(
		sortable: SortableModel,
		mayBeFocusedIndices: List[Int],
		focusedIndicesChanged: Boolean
	): Either[SortedModelError, SortedModel] =
		if(mayBeFocusedIndices.length <= 0) Left(SortedModelError.ToFewChangedIndices(mayBeFocusedIndices))
		else if(mayBeFocusedIndices.exists(_ < 0)) Left(SortedModelError.NegativeChangedIndices(mayBeFocusedIndices))
		else Right(
			SortedModel(
				sortable = sortable,
				focusedIndices = mayBeFocusedIndices,
				focusedIndicesChanged = focusedIndicesChanged
			)
		)


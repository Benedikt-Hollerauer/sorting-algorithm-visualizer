package core.model

import error.modelError.SortedModelError

case class SortedModel private(
	sortableWithIndex: List[ValueWithIndexModel],
	focusedIndices: List[Int],
	focusedIndicesChanged: Boolean
)

object SortedModel:

	def from(
		sortable: SortableModel,
		mayBeFocusedIndices: List[Int],
		focusedIndicesChanged: Boolean
	): Either[SortedModelError, SortedModel] =
		if(mayBeFocusedIndices.length <= 1) Left(SortedModelError.ToFewChangedIndices(mayBeFocusedIndices))
		else if(mayBeFocusedIndices.exists(_ < 0)) Left(SortedModelError.NegativeChangedIndices(mayBeFocusedIndices))
		else Right(
			SortedModel(
				sortableWithIndex = sortable.valuesWithIndices
					.map: valueWithIndex =>
						ValueWithIndexModel(
							value = valueWithIndex.value,
							indexModel = valueWithIndex.indexModel
						),
				focusedIndices = mayBeFocusedIndices,
				focusedIndicesChanged = focusedIndicesChanged
			)
		)

	def from(
		sortable: List[ValueWithIndexModel],
		mayBeFocusedIndices: List[Int], // ToDo maybe abstract away this and add tests
		focusedIndicesChanged: Boolean
	): Either[SortedModelError, SortedModel] =
		if (mayBeFocusedIndices.length <= 0) Left(SortedModelError.ToFewChangedIndices(mayBeFocusedIndices))
		else if (mayBeFocusedIndices.exists(_ < 0)) Left(SortedModelError.NegativeChangedIndices(mayBeFocusedIndices))
		else Right(
			SortedModel(
				sortableWithIndex = sortable,
				focusedIndices = mayBeFocusedIndices,
				focusedIndicesChanged = focusedIndicesChanged
			)
		)
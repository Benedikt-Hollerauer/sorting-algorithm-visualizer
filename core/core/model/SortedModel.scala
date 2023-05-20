package core.model

import error.modelError.SortedModelError

case class SortedModel private(
	sortableWithIndex: List[ValueWithIndex],
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
				sortableWithIndex = sortable.list
					.zipWithIndex
					.map: valueWithIndex =>
						ValueWithIndex.from(
							value = valueWithIndex._1,
							mayBeIndex = valueWithIndex._2
						).toOption.get,
				focusedIndices = mayBeFocusedIndices,
				focusedIndicesChanged = focusedIndicesChanged
			)
		)

	def from(
		sortable: List[ValueWithIndex],
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
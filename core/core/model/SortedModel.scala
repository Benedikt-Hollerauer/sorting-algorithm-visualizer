package core.model

import error.modelError.SortedModelError

case class SortedModel private(
	sortable: SortableModel
)

object SortedModel:

	def from(
		sortable: SortableModel,
	): Either[SortedModelError, SortedModel] =
		Right(
			SortedModel(
				sortable = sortable
			)
		)


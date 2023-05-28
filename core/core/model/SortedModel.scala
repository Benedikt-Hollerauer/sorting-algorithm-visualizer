package core.model

import error.modelError.SortedModelError

case class SortedModel(
	sortableModel: SortableModel,
	changes: LazyList[SortingModel]
)
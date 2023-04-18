package error.modelError

enum SortedModelError:

	case ToFewChangedIndices(changedIndices: List[Int])
	
enum SortedModel:

	case ChangedIndices(
		sortable: SortedModel,
		changedIndices: List[Int]
	)
	
	case NoChangedIndices(sortable: SortedModel)
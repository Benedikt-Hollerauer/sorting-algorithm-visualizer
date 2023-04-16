package error.useCaseError

import error.modelError.SortedModelError

enum GenerateSortableUseCaseError:

	case InputFailure(sortableValueError: SortedModelError)
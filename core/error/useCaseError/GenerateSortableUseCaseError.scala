package error.useCaseError

import error.modelError.SortableModelError

enum GenerateSortableUseCaseError:

	case InputFailure(sortableValueError: SortableModelError)
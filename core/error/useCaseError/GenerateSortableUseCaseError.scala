package error.useCaseError

import error.modelError.SortableModelError

enum GenerateSortableUseCaseError:

	case SortableModelCreationFailed(sortableModelError: SortableModelError)
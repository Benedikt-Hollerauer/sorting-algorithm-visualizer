package error.useCaseError

import error.modelError.{NonEmptyListModelError, SortableModelError}

enum GenerateSortableUseCaseError:

	case SortableModelCreationFailed(sortableModelError: SortableModelError)
	case NonEmptyListModelCreationFailed(nonEmptyListModelError: NonEmptyListModelError)
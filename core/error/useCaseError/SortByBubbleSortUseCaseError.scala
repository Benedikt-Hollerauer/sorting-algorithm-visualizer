package error.useCaseError

import error.modelError.{NonEmptyListModelError, SortableModelError}

enum SortByBubbleSortUseCaseError:

    case NonEmptyListModelCreationFailed(nonEmptyListModelError: NonEmptyListModelError)
    case SortableModelCreationFailed(sortableModelError: SortableModelError)
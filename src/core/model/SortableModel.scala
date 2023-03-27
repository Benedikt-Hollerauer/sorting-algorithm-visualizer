package core.model

import error.modelError.SortableModelError

case class SortableValue private(list: List[Int])

object SortableValue:

    def from(mayBeList: List[Int]): Either[SortableModelError, SortableValue] =
        if(mayBeList.isEmpty) Left(SortableModelError.EmptyList(mayBeList))
        else if(mayBeList.length == 1) Left(SortableModelError.ToFewElements(mayBeList))
        else Right(SortableValue(mayBeList))
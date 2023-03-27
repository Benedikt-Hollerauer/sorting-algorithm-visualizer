package core.model

import error.modelError.SortableModelError

case class SortableModel private(list: List[Int])

object SortableModel:

    def from(mayBeList: List[Int]): Either[SortableModelError, SortableModel] =
        if(mayBeList.isEmpty) Left(SortableModelError.EmptyList(mayBeList))
        else if(mayBeList.length == 1) Left(SortableModelError.ToFewElements(mayBeList))
        else Right(SortableModel(mayBeList))
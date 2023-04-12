package core.model

import error.modelError.SortableModelError

case class SortableModel private(list: List[Int])

object SortableModel:

    def from(mayBeList: List[Int]): Either[SortableModelError, SortableModel] =
        if(mayBeList.isEmpty) Left(SortableModelError.EmptyList)
        else if(mayBeList.length == 1) Left(SortableModelError.ToFewElements(mayBeList.length))
        else if(mayBeList.length >= 500) Left(SortableModelError.ToManyElements(mayBeList.length))
        else Right(SortableModel(mayBeList))
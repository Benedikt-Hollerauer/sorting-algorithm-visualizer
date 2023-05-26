package core.model

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError

case class SortableModel private(
    valuesWithIndices: NonEmptyListModel[ValueWithIndexModel]
)

object SortableModel: // TODO mayBeList should be a List[ValueWithIndexModel[ValueWithIndexModel[ValueWithIndexModel[ValueWithIndexModel]]]]

    def from(mayBeList: NonEmptyListModel[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeList.list.length > 500) Left(SortableModelError.ToManyElements(mayBeList.list.length))
        else Right(
            SortableModel(
                valuesWithIndices = mayBeList
            )
        )
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveElement
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortableModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock

object Main:

    def main(args: Array[String]): Unit =
        val sortable = GenerateSortableUseCase(
            GenerateSortableInputMock.success
        ).left.map(_ match
            case GenerateSortableUseCaseError.InputFailure(value) => value
        )
        SortByBubbleSortUseCase(
            SortByBubbleSortInput(
                sortable,
                OrderModel.Ascending
            )
        ) match
            case Left(error) =>
                renderOnDomContentLoaded(
                    dom.document.body,
                    h1(
                        "there was an error: " + error.toString
                    )
                )
            case Right(res) =>
                renderOnDomContentLoaded(
                    dom.document.body,
                    div(
                        res.map(x => div(x.list.map(_.toString).reduce((x, y) => x + " - " + y), whiteSpace := "nowrap"))
                    )
                )
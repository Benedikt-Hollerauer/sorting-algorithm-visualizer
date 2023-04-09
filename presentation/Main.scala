import com.raquo.laminar.api.L.{*, given}
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortableModel}
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase
import mock.ToBeSortedMock

object Main:

    def main(args: Array[String]): Unit =
        SortByBubbleSortUseCase(
            SortByBubbleSortInput(
                SortableModel.from(ToBeSortedMock.ascendingOrder.unsorted),
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
                        res.map(x => div(x.toString))
                    )
                )

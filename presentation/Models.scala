case class VisualModel(
	src: String,
	alt: String,
	link: Option[String] = None
)

enum SortingAlgorithm(name: String):
	
	case BubbleSort extends SortingAlgorithm(name = "Bubble Sort")
	case InsertionSort extends SortingAlgorithm(name = "Insertion Sort")
	
	def getName: String =
		this.name
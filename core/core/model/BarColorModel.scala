package core.model

enum BarColorModel(color: String):

	def getColor: String =
		this.color

	case Blue extends BarColorModel("Blue")
	case Red extends BarColorModel("Red")
package demo

import org.localstorm.platform.dsl.fbp.*

Component.declarations(
	Cool   : stdlib.CsvAdapter,
	Uncool : stdlib.JmsAdapter
)

cool = "Cool".component(
    filename: 'file.csv',
    pollForUpdates: 1000
)

uncool = "Uncool".component(
    filename : (args.length>0) ? args[0] : null
)

uncoolOutput = uncool.port("OUTPUT")
coolInput    = cool.port("INPUT")

//uncoolOutput >> coolInput >> $.out

// delegation
compositeOutput = Boundary.port("OUTPUT")
compositeOutput > uncoolOutput

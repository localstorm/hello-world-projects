package demo

import org.localstorm.platform.dsl.fbp.*

Component.declarations(
	Cool   : stdlib.CsvAdapter,
	Uncool : stdlib.JmsAdapter
)

cool = "Cool".component(
    fileName: 'file.csv',
    pollForUpdates: 1000
)

uncool = "Uncool".component(
    compositeName : (args.length>0) ? args[0] : null
)

uncoolOutput = uncool.port("OUTPUT")
coolInput    = cool.port("INPUT")

uncoolOutput >> coolInput

// delegation
compositeOutput = Boundary.port("OUTPUT")
compositeOutput >> uncoolOutput

println "${args[0]} was initiallized with fileName="+("fileName".attribute)
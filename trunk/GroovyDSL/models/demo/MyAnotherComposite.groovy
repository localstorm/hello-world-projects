package demo

import org.localstorm.platform.dsl.fbp.*

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
compositeInput = Boundary.port("INPUT")
compositeInput > coolInput  

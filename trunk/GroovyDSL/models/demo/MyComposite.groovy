package demo

import org.localstorm.groovy.dsl.fbp.*

cool = "Cool".component(
    filename: 'file.csv',
    pollForUpdates: 1000
)

uncool = "Uncool".component(
    filename : (args.length>0) ? args[0] : null
)

uncoolOutput = uncool.port("OUTPUT")
coolInput    = cool.port("INPUT")

uncoolOutput >> coolInput >> $.out


// delegation
/*
input = Boundary.port("INPUT")
input <=> coolInput 

output = Boundary.port("OUTPUT")
output <=> uncoolOutput
*/
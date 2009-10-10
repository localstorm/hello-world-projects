package org.localstorm.groovy.dsl

import org.localstorm.groovy.dsl.fbp.*

DSL.init()

cool = "Cool".component([
    filename: 'file.csv',
    pollForUpdates: 1000
])

uncool = "Uncool".component()

uncool.filename = 'badfile.csv'

uncoolOutput = uncool.port("OUTPUT")
coolInput    = cool.port("INPUT")

uncoolOutput >> coolInput >> $.out







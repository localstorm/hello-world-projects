package demo

import org.localstorm.platform.dsl.fbp.*
import org.localstorm.components.java.*
import org.localstorm.components.groovy.*

DSL.init()

Component.declarations(
	GroovyBased:		stdlib.GroovyComponent,
	MyComposite:        MyComposite.class,
    MyAnotherComposite: MyAnotherComposite.class
)

myComposite 	   = "MyComposite".component(fileName: "data.csv")
myAnotherComposite = "MyAnotherComposite".component

myComposite.port("OUTPUT") >> myAnotherComposite.port("INPUT")

groovy = "GroovyBased".component
groovy.fileName = myComposite.fileName



package demo

import org.localstorm.groovy.dsl.fbp.*

DSL.init()

Component.declarations(
//    Cool:               "CSVAdapter".import,
//    Uncool:             "JMSAdapter".import,
    MyComposite:        MyComposite.class,
    MyAnotherComposite: MyAnotherComposite.class
)

myComposite = "MyComposite".component
myAnotherComposite = "MyAnotherComposite".component

myComposite.port("OUTPUT") >> myAnotherComposite.port("INPUT")




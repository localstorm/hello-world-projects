package demo

import org.localstorm.platform.dsl.fbp.*;

DSL.init()

Component.declarations(
    MyComposite:        MyComposite.class,
    MyAnotherComposite: MyAnotherComposite.class
)

myComposite = "MyComposite".component
myAnotherComposite = "MyAnotherComposite".component

myComposite.port("OUTPUT") >> myAnotherComposite.port("INPUT")




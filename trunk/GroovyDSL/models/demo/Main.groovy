package demo

import org.localstorm.groovy.dsl.fbp.*

DSL.init()

Component.declarations(
    MyComposite: MyComposite.class,
    MyAnotherComposite: MyAnotherComposite.class
)

"MyComposite".component().port("OUTPUT") >> "MyAnotherComposite".component().port("INPUT")




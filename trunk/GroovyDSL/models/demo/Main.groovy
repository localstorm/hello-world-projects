package demo

import org.localstorm.groovy.dsl.fbp.*

DSL.init()

new Components(
    MyComposite: MyComposite.class,
    MyAnotherComposite: MyAnotherComposite.class
)

"MyComposite".component().port("OUTPUT") >> "MyAnotherComposite".component().port("INPUT")




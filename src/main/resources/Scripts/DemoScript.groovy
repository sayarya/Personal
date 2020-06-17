package Scripts

import groovy.transform.BaseScript

class Script extends groovy.lang.Script{

    @Override
    Object run() {
        return "Hey"
    }
}
//abstract class CustomScript extends Script{

 //int write(){42}

//}
@BaseScript CustomScriptBody baseScript
//
//assert baseScript==this
//assert  write()==42
//assert write()==baseScript.write()

abstract class CustomScriptBody extends Script{
    abstract def runScript()
    def pre(){println "pre"}
    def post(){println "post"}
    def run() {
        pre()
        try {
            5.times { runScript() }
        }
        finally {
            post()
        }
    }
}
@BaseScript CustomScriptBody baseScript2
println "Script body run"

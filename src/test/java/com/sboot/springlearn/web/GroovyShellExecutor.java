package com.sboot.springlearn.web;
import java.util.*;
import groovy.lang.GroovyShell ;
import groovy.lang.GroovyClassLoader ;
import groovy.util.GroovyScriptEngine ;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File ;

class GroovyShellExecutor {

    static void runWithGroovyShell() throws Exception {

        new GroovyShell().evaluate( new File( "C:/Users/Arya/Desktop/test.groovy" ) ) ;

    }
    // Used to invoke demo method in test.groovy
    static void runWithGroovyClassLoader() throws Exception {
        // Declaring a class to conform to a java interface class would get rid of
        // a lot of the reflection here
        Class scriptClass = new GroovyClassLoader().parseClass( new File( "C:/Users/Arya/Downloads/spring-learn/src/main/resources/Scripts/DemoScript.groovy" ) ) ;
        Object scriptInstance = scriptClass.newInstance() ;
        scriptClass.getDeclaredMethod( "runScript", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
    }
    // Used to invoke demo method in test.groovy
    static void runWithGroovyScriptEngine() throws Exception {
        // Declaring a class to conform to a java interface class would get rid of
        // a lot of the reflection here
        Class scriptClass = new GroovyScriptEngine( "." ).loadScriptByName( "./src/main/resources/Scripts/DemoScript.groovy" ) ;
        Object scriptInstance = scriptClass.newInstance() ;
        scriptClass.getDeclaredMethod( "runScript", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
    }

    public static void main( String[] args ) throws Exception {
        runWithGroovyShell() ;
        runWithGroovyClassLoader() ;
      runWithGroovyScriptEngine() ;
    }
}
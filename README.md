# FMI4j #

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.mechatronics.sfi.fmi4j/fmi-import/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.mechatronics.sfi.fmi4j/fmi-import)


FMI4j is a software library for dealing with Functional Mock-up Units in Kotlin/Java.
Currently it consists of two parts:

## FMI Import

Allows FMUs for Model Exchange and Co-simulation (version 2.0 only) to be imported in Java applications.
For Model Exchange, solvers are also included


#### Co-simulation example

```java

FmiSimulation fmu = new CoSimulationFmu(new File("path/to/fmu.fmu"));

//or
// fmu = CoSimulationFmu.newBuilder(new File(...))
//  .loggingOn(true)
// .build()

fmu.init();

double dt = 1d/100;
while (fmu.getCurrentTime() < 10) {
    fmu.doStep(dt);
}

fmu.terminate();

```


#### Model-exchange(with integrator) example

```java

FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(1E-3);
FmiSimulation fmu = new ModelExchangeFmuWithIntegrator(new File("path/to/fmu.fmu"), integrator);
fmu.init();

double dt = 1d/100;
while (fmu.getCurrentTime() < 5) {
    fmu.step(dt);
}

fmu.terminate();

```

## FMU2Jar

Command line tool for packaging an FMU into a Java library. This allows you to use the FMU as any other Java library. 
The generated library also exposes all variables from the FMU through a type safe API.

E.g. an FMU with a variable named "Controller.speed" of type Real, will have the methods

```java
    public double getController_speed()
    public void setController_speed(double value)
``` 

### Usage

```
fmi2jar -fmu "fmu/location.fmu" -out "where/to/put/generated/jar"
```
add ```-mavenLocal``` if you want the .jar to be installed in your local maven repository

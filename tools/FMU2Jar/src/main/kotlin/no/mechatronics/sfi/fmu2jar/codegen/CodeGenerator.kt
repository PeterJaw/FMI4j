/*
 * The MIT License
 *
 * Copyright 2017-2018 Norwegian University of Technology
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package no.mechatronics.sfi.fmu2jar.codegen

import no.mechatronics.sfi.fmi4j.modeldescription.ModelDescription
import no.mechatronics.sfi.fmi4j.modeldescription.ModelDescriptionProvider
import no.mechatronics.sfi.fmi4j.modeldescription.variables.*

/**
 * @author Lars Ivar Hatledal
 */
class CodeGenerator(
        private val md: ModelDescriptionProvider
) {

    private val modelName = md.modelName

    fun generateBody(): String {

        var solverImport = ""
        if (md.supportsModelExchange) {
            solverImport = "import no.mechatronics.sfi.fmi4j.solvers.Solver"
        }

        return """
package no.mechatronics.sfi.fmu2jar;

import java.net.URL;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Iterator;
import java.io.IOException;
import no.mechatronics.sfi.fmi4j.common.*;
import no.mechatronics.sfi.fmi4j.importer.*;
import no.mechatronics.sfi.fmi4j.modeldescription.*;
import no.mechatronics.sfi.fmi4j.modeldescription.variables.*;
$solverImport

/***
 * Class wrapping FMU named '$modelName' auto-generated by FMU2Jar
 *
 * @author Lars Ivar Hatledal
 */
public class $modelName implements FmuSlave {

    private static Fmu fmu = null;
    private final FmuSlave slave;

    private $modelName(FmuSlave slave) {
        this.slave = slave;
    }

    private static Fmu getOrCreateFmu() {
        if (fmu == null) {
            try {
                URL url = $modelName.class.getClassLoader().getResource("$modelName.fmu");
                fmu = Fmu.from(url);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return fmu;
    }

    ${generateFactories()}

    @Override
    public CoSimulationModelDescription getModelDescription() {
        return slave.getModelDescription();
    }

    @Override
    public ModelVariables getModelVariables() {
        return slave.getModelVariables();
    }

    @Override
    public FmiStatus getLastStatus() {
        return slave.getLastStatus();
    }

    @Override
    public boolean isTerminated() {
        return slave.isTerminated();
    }

    @Override
    public double getSimulationTime() {
        return slave.getSimulationTime();
    }

    @Override
    public boolean simpleSetup() {
        return simpleSetup(0.0);
    }

    public boolean simpleSetup(double start) {
        return simpleSetup(start, 0.0);
    }

    public boolean simpleSetup(double start, double stop) {
        return simpleSetup(start, stop, 0.0);
    }

    @Override
    public boolean simpleSetup(double start, double stop, double tolerance) {
        return slave.setup(start, stop, tolerance) && slave.enterInitializationMode() && slave.exitInitializationMode();
    }

    @Override
    public boolean setup() {
        return setup(0.0);
    }

    public boolean setup(double start) {
        return setup(start, 0.0);
    }

    public boolean setup(double start, double stop) {
        return setup(start, stop, 0.0);
    }

    @Override
    public boolean setup(double start, double stop, double tolerance) {
        return slave.setup(start, stop, tolerance);
    }

    @Override
    public boolean enterInitializationMode() {
        return slave.enterInitializationMode();
    }

    @Override
    public boolean exitInitializationMode() {
        return slave.exitInitializationMode();
    }

    @Override
    public boolean doStep(double stepSize) {
        return slave.doStep(stepSize);
    }

    @Override
    public boolean cancelStep() {
        return slave.cancelStep();
    }

    @Override
    public boolean terminate() {
        return slave.terminate();
    }

    @Override
    public boolean reset() {
        return slave.reset();
    }

    @Override
    public double[] getDirectionalDerivative(long[] vUnknownRef, long[] vKnownRef, double[] dvKnown) {
        return slave.getDirectionalDerivative(vUnknownRef, vKnownRef, dvKnown);
    }

    @Override
    public long getFMUstate() {
        return slave.getFMUstate();
    }

    @Override
    public boolean setFMUstate(long state) {
        return slave.setFMUstate(state);
    }

    @Override
    public boolean freeFMUstate(long state) {
        return slave.freeFMUstate(state);
    }

    @Override
    public byte[] serializeFMUstate(long state) {
        return slave.serializeFMUstate(state);
    }

    @Override
    public long deSerializeFMUstate(byte[] state) {
        return slave.deSerializeFMUstate(state);
    }

    @Override
    public FmuVariableAccessor getVariableAccessor() {
        return slave.getVariableAccessor();
    }

    @Override
    public void close() {
        slave.close();
    }

    private Locals locals;
    private Inputs inputs;
    private Outputs outputs;
    private Parameters parameters;
    private CalculatedParameters calculatedParameters;

    /**
     * Access variables with causality=LOCAL
     */
    public Locals getLocals() {
        if (locals == null) {
            locals = new Locals();
        }
        return locals;
    }

    /**
     * Access variables with causality=INPUT
     */
    public Inputs getInputs() {
     if (inputs == null) {
            inputs = new Inputs();
        }
        return inputs;
    }

    /**
     * Access variables with causality=OUTPUT
     */
    public Outputs getOutputs() {
     if (outputs == null) {
            outputs = new Outputs();
        }
        return outputs;
    }

    /**
     * Access variables with causality=PARAMETER
     */
    public Parameters getParameters() {
     if (parameters == null) {
            parameters = new Parameters();
        }
        return parameters;
    }

    /**
     * Access variables with causality=CALCULATED_PARAMETER
     */
    public CalculatedParameters getCalculatedParameters() {
     if (calculatedParameters == null) {
            calculatedParameters = new CalculatedParameters();
        }
        return calculatedParameters;
    }

    public class AbstractParameters implements Iterable<TypedScalarVariable<?>> {

        private final List<TypedScalarVariable<?>> vars;

        private AbstractParameters(Causality causality) {
            this.vars = getModelVariables().getByCausality(causality);
        }

        public int size() {
            return vars.size();
        }

        public List<TypedScalarVariable<?>> get() {
            return vars;
        }

        @Override
        public Iterator<TypedScalarVariable<?>> iterator() {
            return vars.iterator();
        }

    }

    public class Inputs extends AbstractParameters {

        private Inputs() {
            super(Causality.INPUT);
        }
        ${generateAccessors(Causality.INPUT)}

    }

    public class Outputs extends AbstractParameters {

        private Outputs() {
            super(Causality.OUTPUT);
        }
        ${generateAccessors(Causality.OUTPUT)}

    }

    public class Parameters extends AbstractParameters {

        private Parameters() {
            super(Causality.PARAMETER);
        }
        ${generateAccessors(Causality.PARAMETER)}

    }

    public class CalculatedParameters extends AbstractParameters {

        private CalculatedParameters() {
            super(Causality.CALCULATED_PARAMETER);
        }
        ${generateAccessors(Causality.CALCULATED_PARAMETER)}

    }

    public class Locals extends AbstractParameters {

        private Locals() {
            super(Causality.LOCAL);
        }
        ${generateAccessors(Causality.LOCAL)}

    }

}

"""

    }

    private fun generateFactories(): String {

        var result = ""
        if (md.supportsCoSimulation) {
            result += """

            public static $modelName newInstance() {
                FmuSlave slave = getOrCreateFmu().asCoSimulationFmu().newInstance();
                return new $modelName(Objects.requireNonNull(slave));
            }
            """

        }

        if (md.supportsModelExchange) {

            result += """

            public static $modelName newInstance(Solver solver) {
                FmuSlave slave = getOrCreateFmu().asModelExchangeFmu(solver).newInstance()
                return new $modelName(Objects.requireNonNull(slave));
            }
            """

        }

        return result

    }

    private fun generateAccessors(causality: Causality): String {

        return StringBuilder().also { sb ->

            sb.append("\n")

            md.modelVariables.getByCausality(causality).forEach { v ->

                if (!v.name.contains("[")) {

                    val functionName = v.name.replace(".", "_").decapitalize()

                    sb.append("""
                    |${generateJavaDoc(v)}
                    |public ${v.typeName}Variable $functionName() {
                    |    return slave.getModelDescription().getModelVariables().getByName("${v.name}").as${v.typeName}Variable();
                    |}
                    |
                    """.trimMargin())

                }


            }

        }.toString()

    }

    private fun generateJavaDoc(v: TypedScalarVariable<*>) : String {

        val star = " *"
        val newLine = "\n$star\n"
        return StringBuilder().apply {

            append("\n")
            append("/**\n")
            append("$star ").append("Name:").append(v.name)

            v.start?.also { append(newLine).append("$star Start=$it") }
            v.causality?.also { append(newLine).append("$star Causality=$it") }
            v.variability?.also { append(newLine).append("$star Variability=$it") }
            v.initial?.also { append(newLine).append("$star Initial=$it") }

            when (v) {
                is IntegerVariable -> {
                    v.min?.also { append(newLine).append("$star min=$it") }
                    v.max?.also { append(newLine).append("$star max=$it") }
                }
                is RealVariable -> {
                    v.min?.also { append(newLine).append("$star min=$it") }
                    v.max?.also { append(newLine).append("$star max=$it") }
                    v.nominal?.also { append(newLine).append("$star nominal=$it") }
                    v.unbounded?.also { append(newLine).append("$star unbounded=$it") }
                }
                is EnumerationVariable -> {
                    v.min?.also { append("$star min=") }
                    v.max?.also { append("$star max=") }
                    v.quantity?.also { append("$star quantity=") }
                }
            }

            v.description?.also { append(newLine).append("$star ").append("Description: $it") }

            append("\n */")

        }.toString()

    }

}


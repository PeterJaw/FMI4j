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

package no.mechatronics.sfi.fmi4j.modeldescription.log

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute

/**
 *
 * @author Lars Ivar Hatledal
 */
interface LogCategory {

    /**
     * Name of the Category element.
     * Must be unique
     */
    val name: String

    /**
     * Optional description of the log category
     */
    val description: String?

}

/**
 * @author Lars Ivar Hatledal
 */
@XmlAccessorType(XmlAccessType.FIELD)
class LogCategoryImpl: LogCategory, Serializable {

    @XmlAttribute
    override lateinit var name: String

    @XmlAttribute
    override val description: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogCategoryImpl

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "LogCategoryImpl(name=$name, description=$description)"
    }

}

/**
 * Log all events (during initialization and simulation).
 */
const val LOG_EVENTS = "logEvents"

/**
 * Log the solution of linear systems of equations if the solution is singular
 * (and the tool picked one solution of the infinitely many solutions).
 */
const val LOG_SINGULAR_LINEAR_SYSTEMS = "logSingularLinearSystems"

/**
 * Log the solution of nonlinear systems of equations.
 */
const val LOG_NON_LINEAR_SYSTEMS ="logNonlinearSystems"

/**
 * Log the dynamic selection of states.
 */
const val LOG_DYNAMIC_STATE_SELECTION = "logDynamicStateSelection"

/**
 * Log messages when returning fmi2Warning status from any function.
 */
const val LOG_STATUS_WARNING = "logStatusWarning"

/**
 * Log messages when returning fmi2Discard status from any function.
 */
const val LOG_STATUS_DISCARD = "logStatusDiscard"

/**
 * Log messages when returning fmi2Error status from any function.
 */
const val LOG_STATUS_ERROR = "logStatusError"

/**
 * Log messages when returning fmi2Fatal status from any function.
 */
const val LOG_STATUS_FATAL = "logStatusFatal"

/**
 * Log messages when returning fmi2Pending status from any function.
 */
const val LOG_STATUS_PENDING = "logStatusPending"

/**
 * Log all messages
 */
const val LOG_ALL = "logAll"




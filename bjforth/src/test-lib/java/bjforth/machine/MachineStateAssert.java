/*
 * Copyright 2022 Bahman Movaqar
 *
 * This file is part of BJForth.
 *
 * BJForth is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BJForth is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BJForth. If not, see <https://www.gnu.org/licenses/>.
 */
package bjforth.machine;

import static bjforth.machine.MachineStateBuilder.aMachineState;
import static bjforth.machine.MachineStateInspectionUtils.*;

import org.assertj.core.api.AbstractAssert;

public class MachineStateAssert extends AbstractAssert<MachineStateAssert, MachineState> {

  protected MachineStateAssert(MachineState actual, Class<MachineStateAssert> selfType) {
    super(actual, selfType);
  }

  public MachineStateAssert(MachineState actual) {
    super(actual, MachineStateAssert.class);
  }

  public MachineStateAssert hasDictionaryEqualTo(MachineState other) {
    isNotNull();
    var actualKeys = dictionaryKeys(actual);
    var otherKeys = dictionaryKeys(other);
    if (actualKeys.size() != otherKeys.size()
        || !actualKeys.stream().allMatch(otherKeys::contains))
    {
      failWithMessage("Expected Dictionary to have keys <%s> but was <%s>", otherKeys, actualKeys);
    }
    for (var k : actualKeys) {
      var actualValue = actual.getDictionary().get(k);
      var otherValue = other.getDictionary().get(k);
      if (!actualValue.equals(otherValue)) {
        failWithMessage("Expected Dictionary to have mapping <%s -> %s> but was <%s -> %s>", k,
            otherValue, k, actualValue);
      }
    }
    return this;
  }

  public MachineStateAssert hasMemoryEqualTo(MachineState other) {
    isNotNull();
    var actualAddrs = memoryAddresses(actual);
    var otherAddrs = memoryAddresses(other);
    if (actualAddrs.size() != otherAddrs.size()
        || !actualAddrs.stream().allMatch(otherAddrs::contains))
    {
      failWithMessage("Expected Memory to have addresses <%s> but was <%s>", otherAddrs,
          actualAddrs);
    }
    for (var addr : actualAddrs) {
      var actualValue = actual.getMemory().get(addr);
      var otherValue = other.getMemory().get(addr);
      if (!actualValue.equals(otherValue)) {
        failWithMessage("Expected Memory address <%s> to be <%s> but was <%s>", addr, otherValue,
            actualValue);
      }
    }
    return this;
  }

  public MachineStateAssert hasParameterStackEqualTo(MachineState other) {
    isNotNull();
    var actualIterator = parameterStackDescendingIterator(actual);
    var otherIterator = parameterStackDescendingIterator(other);
    while (otherIterator.hasNext()) {
      if (!actualIterator.hasNext()) {
        failWithMessage("Expected ParameterStack to contain <%s> but there were no more elements.",
            otherIterator.next());
      }
      var actualValue = actualIterator.next();
      var otherValue = otherIterator.next();
      if (!actualValue.equals(otherValue)) {
        failWithMessage("Expected ParameterStack to contain <%s> but was <%s>.", otherValue,
            actualValue);
      }
    }
    if (actualIterator.hasNext()) {
      failWithMessage("Expected ParameterStack to have no more contents but was <%s>",
          actualIterator.next());
    }
    return this;
  }

  public MachineStateAssert hasReturnStackEqualTo(MachineState other) {
    isNotNull();
    var actualIterator = returnStackDescendingIterator(actual);
    var otherIterator = returnStackDescendingIterator(other);
    while (otherIterator.hasNext()) {
      if (!actualIterator.hasNext()) {
        failWithMessage("Expected ReturnStack to contain <%s> but there were no more elements.",
            otherIterator.next());
      }
      var actualValue = actualIterator.next();
      var otherValue = otherIterator.next();
      if (!actualValue.equals(otherValue)) {
        failWithMessage("Expected ReturnStack to contain <%s> but was <%s>.", otherValue,
            actualValue);
      }
    }
    if (actualIterator.hasNext()) {
      failWithMessage("Expected ReturnStack to have no more elements but was <%s>",
          actualIterator.next());
    }
    return this;
  }

  public MachineStateAssert hasReturnStackEqualTo(Stack<Integer> otherStack) {
    var otherMs = aMachineState().withReturnStack(otherStack).build();
    return hasReturnStackEqualTo(otherMs);
  }

  public MachineStateAssert hasInstructionPointerEqualTo(MachineState other) {
    var actualIp = actual.getInstructionPointer();
    var otherIp = other.getInstructionPointer();
    if (!actualIp.equals(otherIp)) {
      failWithMessage("Expected instructionPointer to be <%s> but was <%s>", otherIp, actualIp);
    }
    return this;
  }

  public MachineStateAssert hasInstructionPointerEqualTo(Integer otherIp) {
    var otherMs = aMachineState().withInstrcutionPointer(otherIp).build();
    return hasInstructionPointerEqualTo(otherMs);
  }

  public MachineStateAssert hasForthInstructionPointerEqualTo(MachineState other) {
    var actualFip = actual.getForthInstructionPointer();
    var otherFip = other.getForthInstructionPointer();
    if (!actualFip.equals(otherFip)) {
      failWithMessage("Expected forthInstructionPointer to be <%s> but was <%s>", otherFip,
          actualFip);
    }
    return this;
  }

  public MachineStateAssert hasForthInstructionPointerEqualTo(Integer otherFip) {
    var otherMs = aMachineState().withForthInstructionPointer(otherFip).build();
    return hasForthInstructionPointerEqualTo(otherMs);
  }
}
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
package bjforth.primitives;

import static bjforth.machine.InstructionPointerBuilder.anInstructionPointer;
import static bjforth.machine.MachineAssertions.*;
import static bjforth.machine.MachineBuilder.aMachine;
import static bjforth.machine.MachineStateBuilder.aMachineState;
import static bjforth.machine.MemoryBuilder.aMemory;
import static bjforth.machine.NextInstructionPointerBuilder.aNextInstructionPointer;
import static bjforth.machine.ParameterStackBuilder.aParameterStack;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bjforth.bootstrap.Bootstrap;
import bjforth.machine.MachineException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NUMBERTest {

  @Test
  @DisplayName("Convert top of ParameterStack to number.")
  public void worksOk() {
    // GIVEN
    var number = PrimitiveFactory.NUMBER();
    var numberAddr = nextInt();
    var ip = anInstructionPointer().with(numberAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var parameter = nextInt();
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(numberAddr, number).build())
            .withParameterStack(aParameterStack().with(Integer.toString(parameter)).build())
            .build();
    var bootstrap = new Bootstrap();
    // TODO Find a cleaner way to bootstrap `state1`
    var _machine = aMachine().withState(state1).build();
    bootstrap.apply(_machine);
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();
    bootstrap.apply(machine);

    // WHEN
    machine.step();

    // THEN
    assertThat(state2)
        .hasInstructionPointerEqualTo(anInstructionPointer().with(state1).plus(1).build())
        .hasNextInstructionPointerEqualTo(aNextInstructionPointer().with(state1).plus(1).build())
        .hasDictionaryEqualTo(state1)
        .hasMemoryEqualTo(state1)
        .hasParameterStackEqualTo(aParameterStack().with(parameter).with(0).build())
        .hasReturnStackEqualTo(state1);
  }

  @Test
  @DisplayName("Attempt to convert an invalid parameter to a number.")
  public void invalidParameter() {
    // GIVEN
    var number = PrimitiveFactory.NUMBER();
    var numberAddr = nextInt();
    var ip = anInstructionPointer().with(numberAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var parameter = RandomStringUtils.secure().next(4);
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(numberAddr, number).build())
            .withParameterStack(aParameterStack().with(parameter).build())
            .build();
    var bootstrap = new Bootstrap();
    // TODO Find a cleaner way to bootstrap `state1`
    var _machine = aMachine().withState(state1).build();
    bootstrap.apply(_machine);
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();
    bootstrap.apply(machine);

    // WHEN
    machine.step();

    // THEN
    assertThat(state2)
        .hasInstructionPointerEqualTo(anInstructionPointer().with(state1).plus(1).build())
        .hasNextInstructionPointerEqualTo(aNextInstructionPointer().with(state1).plus(1).build())
        .hasDictionaryEqualTo(state1)
        .hasMemoryEqualTo(state1)
        .hasParameterStackEqualTo(aParameterStack().with((Object) null).with(-1).build())
        .hasReturnStackEqualTo(state1);
  }

  @Test
  @DisplayName("Should throw if ParameterStack is already empty.")
  public void throwIfEmpty() {
    // GIVEN
    var number = PrimitiveFactory.NUMBER();
    var numberAddr = nextInt();
    var ip = anInstructionPointer().with(numberAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(numberAddr, number).build())
            .withParameterStack(aParameterStack().build())
            .build();
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();

    // EXPECT
    assertThrows(MachineException.class, machine::step);
    assertThat(state2).isEqualTo(aMachineState().copyFrom(state1).build());
  }
}

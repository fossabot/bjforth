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

import static bjforth.machine.DictionaryBuilder.aDictionary;
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

import bjforth.machine.DictionaryItem;
import bjforth.machine.MachineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FINDTest {

  @Test
  @DisplayName("Find a dictionary item.")
  public void worksOk() {
    // GIVEN
    var find = PrimitiveFactory.FIND();
    var findAddr = nextInt();
    var ip = anInstructionPointer().with(findAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var wordToFind = "ADD";
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(findAddr, find).with(100, PrimitiveFactory.ADD()).build())
            .withParameterStack(aParameterStack().with(wordToFind).build())
            .withDictionary(
                aDictionary()
                    .with(wordToFind, new DictionaryItem(wordToFind, 100, false, false))
                    .build())
            .build();
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();

    // WHEN
    machine.step();

    // THEN
    assertThat(state2)
        .hasInstructionPointerEqualTo(anInstructionPointer().with(state1).plus(1).build())
        .hasNextInstructionPointerEqualTo(aNextInstructionPointer().with(state1).plus(1).build())
        .hasDictionaryEqualTo(state1)
        .hasMemoryEqualTo(state1)
        .hasParameterStackEqualTo(aParameterStack().with(100).build())
        .hasReturnStackEqualTo(state1);
  }

  @Test
  @DisplayName("Should ignore hidden dictionary entries.")
  public void ignoreHidden() {
    // GIVEN
    var find = PrimitiveFactory.FIND();
    var findAddr = nextInt();
    var ip = anInstructionPointer().with(findAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var wordToFind = "ADD";
    var isHidden = true;
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(findAddr, find).with(100, PrimitiveFactory.ADD()).build())
            .withParameterStack(aParameterStack().with(wordToFind).build())
            .withDictionary(
                aDictionary()
                    .with(wordToFind, new DictionaryItem(wordToFind, 100, false, isHidden))
                    .build())
            .build();
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();

    // WHEN
    machine.step();

    // THEN
    assertThat(state2)
        .hasInstructionPointerEqualTo(anInstructionPointer().with(state1).plus(1).build())
        .hasNextInstructionPointerEqualTo(aNextInstructionPointer().with(state1).plus(1).build())
        .hasDictionaryEqualTo(state1)
        .hasMemoryEqualTo(state1)
        .hasParameterStackEqualTo(aParameterStack().build())
        .hasReturnStackEqualTo(state1);
  }

  @Test
  @DisplayName("Should throw if ParameterStack is already empty.")
  public void throwIfEmpty() {
    // GIVEN
    var find = PrimitiveFactory.FIND();
    var findAddr = nextInt();
    var ip = anInstructionPointer().with(findAddr).build();
    var nip = aNextInstructionPointer().with(ip).plus(1).build();
    var wordToFind = "ADD";
    var state1 =
        aMachineState()
            .withInstrcutionPointer(ip)
            .withNextInstructionPointer(nip)
            .withMemory(aMemory().with(findAddr, find).with(100, PrimitiveFactory.ADD()).build())
            .withParameterStack(aParameterStack().build())
            .withDictionary(
                aDictionary()
                    .with(wordToFind, new DictionaryItem(wordToFind, 100, false, false))
                    .build())
            .build();
    var state2 = aMachineState().copyFrom(state1).build();
    var machine = aMachine().withState(state2).build();

    // EXPECT
    assertThrows(MachineException.class, machine::step);
    assertThat(state2).isEqualTo(aMachineState().copyFrom(state1).build());
  }
}

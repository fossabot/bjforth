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

import bjforth.machine.Machine;
import bjforth.machine.MachineException;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class MOVE implements MachinePrimitiveWithNext {

  @Override
  public void executeWithNext(Machine machine) {
    try {
      var lengthObject = machine.popFromParameterStack();
      var fromAddrObject = machine.popFromParameterStack();
      var toAddrObject = machine.popFromParameterStack();
      if (lengthObject instanceof Integer length
          && fromAddrObject instanceof Integer fromAddress
          && toAddrObject instanceof Integer toAddress) {
        IntStream.range(0, length)
            .forEach(
                offset -> {
                  var obj = machine.getMemoryAt(fromAddress + offset);
                  machine.setMemoryAt(toAddress + offset, obj);
                });
      } else {
        throw new MachineException("Invalid memory address");
      }
    } catch (NoSuchElementException ex) {
      throw new MachineException("ParameterStack error.");
    }
  }
}

/*
 * Copyright 2024 Bahman Movaqar
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
import bjforth.variables.Variables;
import java.util.NoSuchElementException;

public class COMMA implements Primitive {
  @Override
  public void execute(Machine machine) {
    try {
      var data = machine.popFromParameterStack();
      var HEREAddr = Variables.HERE().getAddress();
      var HEREValue = (Integer) machine.getMemoryAt(HEREAddr);
      machine.setMemoryAt(HEREValue, data);
      var newHEREValue = HEREValue + 1;
      machine.setMemoryAt(HEREAddr, newHEREValue);
    } catch (NoSuchElementException ex) {
      throw new MachineException("ParameterStack error.");
    }
  }
}

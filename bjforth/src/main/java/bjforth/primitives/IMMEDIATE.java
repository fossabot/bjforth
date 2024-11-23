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

public class IMMEDIATE implements Primitive {
  @Override
  public void execute(Machine machine) {
    var LATESTAddr = Variables.LATEST().getAddress();
    var LATESTValue = (Integer) machine.getMemoryAt(LATESTAddr);
    var wordAddr = (Integer) machine.getMemoryAt(LATESTValue);
    var maybeDictItem = machine.getDictionaryItem(wordAddr);
    if (maybeDictItem.isPresent()) {
      var dictItem = maybeDictItem.get();
      dictItem.setIsImmediate(!dictItem.getIsImmediate());
    } else {
      throw new MachineException("DictionaryItem not found.");
    }
  }
}

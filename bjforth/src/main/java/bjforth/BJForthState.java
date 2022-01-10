/*
 * Copyright 2022 Bahman Movaqar.
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
 * along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package bjforth;

public class BJForthState {

  private Integer instructionPointer = 0;
  private final Memory memory = new Memory();
  private final Dictionary dictionary = new Dictionary();

  public Dictionary dictionary() {
    return dictionary;
  }

  public Memory memory() {
    return memory;
  }

  public Integer instructionPointer() {
    return instructionPointer;
  }

  public void instructionPointer(Integer address) {
    instructionPointer = address;
  }
}

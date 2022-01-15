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

class MachineState {
  private Integer instructionPointer = 0;
  private Integer forthInstructionPointer = 0;
  private Memory memory = new Memory();
  private Dictionary dictionary = new Dictionary();
  private Stack<Integer> returnStack = new Stack<>();
  private Stack<Object> parameterStack = new Stack<>();

  MachineState(Integer instructionPointer, Integer forthInstructionPointer, Memory memory,
      Dictionary dictionary, Stack<Integer> returnStack, Stack<Object> parameterStack)
  {
    this.instructionPointer = instructionPointer;
    this.forthInstructionPointer = forthInstructionPointer;
    this.memory = memory;
    this.dictionary = dictionary;
    this.returnStack = returnStack;
    this.parameterStack = parameterStack;
  }

  void setInstructionPointer(Integer instructionPointer) {
    this.instructionPointer = instructionPointer;
  }

  void setForthInstructionPointer(Integer forthInstructionPointer) {
    this.forthInstructionPointer = forthInstructionPointer;
  }

  Integer getInstructionPointer() {
    return instructionPointer;
  }

  Integer getForthInstructionPointer() {
    return forthInstructionPointer;
  }

  Memory getMemory() {
    return memory;
  }

  Dictionary getDictionary() {
    return dictionary;
  }

  Stack<Integer> getReturnStack() {
    return returnStack;
  }

  Stack<Object> getParameterStack() {
    return parameterStack;
  }

}

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

import java.util.ArrayDeque;
import java.util.Deque;

class Stack<T> {
  private final Deque<T> data = new ArrayDeque<>();

  Stack() {
  }

  Stack(Stack<T> other) {
    other.data.descendingIterator().forEachRemaining(data::addFirst);
  }

  public T pop() {
    return data.removeFirst();
  }

  public void push(T item) {
    data.addFirst(item);
  }
}

/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.iti.dexcenter.client;

/**
 * An interface for tables that support column sorting.
 */
public interface HasSortableColumns extends HasFixedColumnWidth {
  /**
   * Add a {@link SortableColumnsListener} listener.
   * 
   * @param listener the listener
   */
  public void addSortableColumnsListener(SortableColumnsListener listener);

  /**
   * Remove a {@link SortableColumnsListener}bleColumnsListener listener.
   * 
   * @param listener the listener to remove
   */
  public void removeSortableColumnsListener(SortableColumnsListener listener);
  
  /**
   * Sort the grid by the specified column.
   * 
   * @param column the column index
   */
  public void sortColumn(int column);
}

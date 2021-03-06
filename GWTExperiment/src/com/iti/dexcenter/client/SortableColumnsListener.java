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

import java.util.EventListener;

/**
 * Event listener interface for column sort events.
 */
public interface SortableColumnsListener extends EventListener {
  /**
   * Fired when the currently sorted column changes.
   * 
   * @param column the currently sorted column, -1 for unsorted
   * @param reversed specifies that this is a reverse sorting
   */
  public abstract void onSetSortedColumn(int column, boolean reversed);
}

package org.fest.swing.driver;

import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns a <code>{@link JToolBar}</code>'s current 
 * UI.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JToolBarUIQuery extends GuiQuery<ToolBarUI> {
  
  private final JToolBar toolBar;

  static ToolBarUI uiOf(JToolBar toolBar) {
    return execute(new JToolBarUIQuery(toolBar));
  }
  
  private JToolBarUIQuery(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  protected ToolBarUI executeInEDT() throws Throwable {
    return toolBar.getUI();
  }
}
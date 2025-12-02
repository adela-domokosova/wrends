/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.1.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.1.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2025 Wren Security. All rights reserved.
 */

package org.opends.guitools.controlpanel.ui.components;

import static org.opends.messages.AdminToolMessages.INFO_CTRL_PANEL_EDIT_BUTTON_LABEL;
import static org.opends.messages.AdminToolMessages.INFO_CTRL_PANEL_NO_VALUE_SPECIFIED;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.opends.guitools.controlpanel.ui.ColorAndFontConstants;
import org.opends.guitools.controlpanel.util.Utilities;

/**
 * Simple cell panel for password attributes: shows a label and an Edit button.
 */
public class PasswordCellPanel extends JPanel
{
  private static final long serialVersionUID = 1L;

  private final JLabel label;
  private final CellEditorButton editButton;

  public PasswordCellPanel()
  {
    super(new GridBagLayout());
    setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;

    label = Utilities.createDefaultLabel(INFO_CTRL_PANEL_NO_VALUE_SPECIFIED.get());
    add(label, gbc);

    gbc.gridx++;
    gbc.weightx = 0.0;
    gbc.insets.left = 5;
    gbc.anchor = GridBagConstraints.NORTH;

    editButton = new CellEditorButton(INFO_CTRL_PANEL_EDIT_BUTTON_LABEL.get());
    editButton.setForeground(ColorAndFontConstants.buttonForeground);
    editButton.setOpaque(false);
    add(editButton, gbc);
  }

  public void setHasPassword(boolean hasPassword)
  {
    if (hasPassword)
    {
      label.setText("********");
    }
    else
    {
      label.setText(INFO_CTRL_PANEL_NO_VALUE_SPECIFIED.get().toString());
    }
  }

  public void addEditActionListener(ActionListener listener)
  {
    editButton.addActionListener(listener);
  }

  public void removeEditActionListener(ActionListener listener)
  {
    editButton.removeActionListener(listener);
  }

  @Override
  protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
      int condition, boolean pressed)
  {
    return editButton.processKeyBinding(ks, e, condition, pressed);
  }

}

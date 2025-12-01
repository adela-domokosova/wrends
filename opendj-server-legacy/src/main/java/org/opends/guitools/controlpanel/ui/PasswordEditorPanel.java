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

package org.opends.guitools.controlpanel.ui;

import static org.opends.messages.AdminToolMessages.ERR_CTRL_PANEL_NEW_PASSWORD_REQUIRED;
import static org.opends.messages.AdminToolMessages.ERR_CTRL_PANEL_PASSWORD_DO_NOT_MATCH;
import static org.opends.messages.AdminToolMessages.INFO_CTRL_PANEL_CHANGE_PASSWORD_TITLE;
import static org.opends.messages.AdminToolMessages.INFO_CTRL_PANEL_PASSWORD_CONFIRM_LABEL;
import static org.opends.messages.AdminToolMessages.INFO_CTRL_PANEL_RESET_USER_PASSWORD_PWD_LABEL;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import org.forgerock.i18n.LocalizableMessage;
import org.opends.guitools.controlpanel.event.ConfigurationChangeEvent;
import org.opends.guitools.controlpanel.util.Utilities;

/**
 * Modal panel used to edit a user password (new + confirm).
 * The modal itself enforces that the two fields match.
 */
public class PasswordEditorPanel extends StatusGenericPanel
{
  private static final long serialVersionUID = 1L;

  private JPasswordField newPasswordField;
  private JPasswordField confirmPasswordField;
  private JLabel newPasswordLabel;
  private JLabel confirmPasswordLabel;

  private boolean valueChanged;
  private String newPassword;

  public PasswordEditorPanel()
  {
    super();
    createLayout();
  }

  @Override
  public Component getPreferredFocusComponent()
  {
    return newPasswordField;
  }

  @Override
  public void cancelClicked()
  {
    reset();
    super.cancelClicked();
  }

  @Override
  public void okClicked()
  {
    // Reset visual state
    setPrimaryValid(newPasswordLabel);
    setPrimaryValid(confirmPasswordLabel);

    final char[] pwd = newPasswordField.getPassword();
    final char[] confirm = confirmPasswordField.getPassword();
    // validate
    if (!Arrays.equals(pwd, confirm))
    {
      showValidationError(ERR_CTRL_PANEL_PASSWORD_DO_NOT_MATCH.get());
      return;
    }
    if (pwd.length == 0)
    {
      showValidationError(ERR_CTRL_PANEL_NEW_PASSWORD_REQUIRED.get());
      return;
    }
    if (errorPane != null)
    {
      errorPane.setVisible(false);
    }
    newPassword = new String(pwd);
    valueChanged = true;
    clearPasswordFields();
    Utilities.getParentDialog(this).setVisible(false);
  }

  @Override
  public LocalizableMessage getTitle()
  {
    return INFO_CTRL_PANEL_CHANGE_PASSWORD_TITLE.get();
  }

  @Override
  public void configurationChanged(ConfigurationChangeEvent ev)
  {
  }

  @Override
  public boolean requiresScroll()
  {
    return false;
  }

  @Override
  public GenericDialog.ButtonType getButtonType()
  {
    return GenericDialog.ButtonType.OK_CANCEL;
  }

  public void reset()
  {
    clearPasswordFields();
    valueChanged = false;
    newPassword = null;

    if (newPasswordLabel != null)
    {
      setPrimaryValid(newPasswordLabel);
    }
    if (confirmPasswordLabel != null)
    {
      setPrimaryValid(confirmPasswordLabel);
    }
    if (errorPane != null)
    {
      errorPane.setVisible(false);
    }
  }

  public boolean valueChanged()
  {
    return valueChanged;
  }

  public String getNewPassword()
  {
    return newPassword;
  }

  private void clearPasswordFields()
  {
    if (newPasswordField != null)
    {
      newPasswordField.setText("");
    }
    if (confirmPasswordField != null)
    {
      confirmPasswordField.setText("");
    }
  }

  private void createLayout()
  {
    GridBagConstraints gbc = new GridBagConstraints();

    // Error pane at the top.
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(10, 10, 0, 10);
    addErrorPane(gbc);

    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 0, 10);

    // New password label
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.weightx = 0.0;
    gbc.anchor = GridBagConstraints.EAST;
    newPasswordLabel = Utilities.createPrimaryLabel(
        INFO_CTRL_PANEL_RESET_USER_PASSWORD_PWD_LABEL.get());
    add(newPasswordLabel, gbc);

    // New password field
    gbc.gridx = 1;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    newPasswordField = Utilities.createPasswordField();
    newPasswordField.setColumns(24);
    add(newPasswordField, gbc);

    // Confirm password label
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.weightx = 0.0;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.EAST;
    confirmPasswordLabel = Utilities.createPrimaryLabel(
        INFO_CTRL_PANEL_PASSWORD_CONFIRM_LABEL.get());
    add(confirmPasswordLabel, gbc);

    // Confirm password field
    gbc.gridx = 1;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    confirmPasswordField = Utilities.createPasswordField();
    confirmPasswordField.setColumns(24);
    add(confirmPasswordField, gbc);

    addBottomGlue(gbc);
  }

  private void showValidationError(LocalizableMessage msg)
  {
    setPrimaryInvalid(newPasswordLabel);
    setPrimaryInvalid(confirmPasswordLabel);
    updateErrorPane(
        errorPane,
        msg,
        ColorAndFontConstants.errorTitleFont,
        LocalizableMessage.raw(""),
        ColorAndFontConstants.defaultFont
    );
    if (errorPane != null)
    {
      errorPane.setVisible(true);
    }
    packParentDialog();
    valueChanged = false;
  }

}

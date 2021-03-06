/*******************************************************************************
 * Mission Control Technologies, Copyright (c) 2009-2012, United States Government
 * as represented by the Administrator of the National Aeronautics and Space 
 * Administration. All rights reserved.
 *
 * The MCT platform is licensed under the Apache License, Version 2.0 (the 
 * "License"); you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under 
 * the License.
 *
 * MCT includes source code licensed under additional open source licenses. See 
 * the MCT Open Source Licenses file included with this distribution or the About 
 * MCT Licenses dialog available at runtime from the MCT Help menu for additional 
 * information. 
 *******************************************************************************/
package gov.nasa.jsc.mct.executable.buttons;

import gov.nasa.arc.mct.components.AbstractComponent;
import gov.nasa.arc.mct.services.component.ComponentRegistry;
import gov.nasa.arc.mct.services.component.CreateWizardUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class CreateExecutableButtonComponentWizardUI extends CreateWizardUI {
		private static final ResourceBundle bundle  = ResourceBundle.getBundle("ResourceBundle"); //NOI18N
		
	    private final JTextField baseDisplayName = new JTextField();
	    private final JTextField execCommand = new JTextField();
		
		@Override
		public AbstractComponent createComp(ComponentRegistry comp,
				AbstractComponent targetComponent) {
			String displayName = baseDisplayName.getText().trim();
			String execCmd = execCommand.getText().trim();
	        AbstractComponent component = null;
	                
	        component = comp.newInstance(ExecutableButtonComponent.class, targetComponent);
	        
	        ExecutableButtonModel execButtonModel = ExecutableButtonComponent.class.cast(component).getModel();
	        execButtonModel.getData().setExecCmd(execCmd);
	        component.setDisplayName(displayName);
			component.save();
	        
	        return component;
		}

		private void setupTextField(String defaultValue, final JTextField field, final JButton create) {
			field.setText(defaultValue); //NOI18N
			field.selectAll();
			field.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					doAction();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					doAction();
				}

				private boolean verify(String input) {
					return input != null && !input.trim().isEmpty();
				}

				private void doAction() {
					boolean flag = verify(execCommand.getText().trim()) && verify(baseDisplayName.getText().trim());
					create.setEnabled(flag);
				}

			});
		}
		
		@Override
		public JComponent getUI(final JButton create) {
			JLabel baseDisplayNameLabel = new JLabel(bundle.getString("BASE_DISPLAY_NAME_LABEL")); //NOI18N
			setupTextField(bundle.getString("DEFAULT_BDN_LABEL"), baseDisplayName, create);
			baseDisplayNameLabel.setLabelFor(baseDisplayName);
			baseDisplayName.requestFocus();
			baseDisplayName.setToolTipText(bundle.getString("BDN_TOOL_TIP"));
		        
			JLabel execCommandLabel = new JLabel(bundle.getString("EXEC_COMMAND_LABEL")); //NOI18N
			setupTextField("", execCommand, create);
			execCommand.setToolTipText(bundle.getString("EXEC_CMD_TOOL_TIP"));
			execCommandLabel.setLabelFor(execCommand);
			
			JPanel messagePanel = new JPanel();
			
			JPanel UIPanel = new JPanel();
			UIPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			c.gridheight = 1;
			c.anchor = GridBagConstraints.SOUTHEAST;
			c.gridy = 0;
			c.gridx = 0;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			c.insets = new Insets (0,5,5,0);
			UIPanel.add(baseDisplayNameLabel, c);
			
			c.gridx = 1;
			c.weightx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.NORTHWEST;
			c.insets = new Insets (5,5,5,5);
			UIPanel.add(baseDisplayName, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.SOUTHEAST;
			c.insets = new Insets (0,5,0,0);
			c.weightx = 0;
			UIPanel.add(execCommandLabel, c);
		        
			c.gridx = 1;
			c.weightx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.NORTHWEST;
			c.insets = new Insets(0,5,0,5);
			UIPanel.add(execCommand, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = 2;
			UIPanel.add(messagePanel,c);
					
			UIPanel.setVisible(true);
			return UIPanel;
		}	

	}

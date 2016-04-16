package update;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GameLoopManager extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private static final String addString = "Add";
	private static final String deleteString = "Delete";
	private static final String upString = "Move up";
	private static final String downString = "Move down";
	private static final String RESOURCE_PACKAGE = "update/";
	private static final String PROPERTIES_FILE = "GameLoopManager";
	private JButton addButton;
	private JButton deleteButton;
	private JButton upButton;
	private JButton downButton;
	private JTextField nameField;

	public GameLoopManager() {
		super(new BorderLayout());
		listModel = new DefaultListModel<String>();
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_PACKAGE + PROPERTIES_FILE);
		for(String key : bundle.keySet()) {
			String[] methods = bundle.getString(key).split(",");
			for(String str: methods) {
				listModel.addElement(key + ": " + str);
			}
		}
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		
		JScrollPane listScrollPane = new JScrollPane(list);
		addButton = new JButton(addString);
		addButton.addActionListener(e -> createAddButton());
		deleteButton = new JButton(deleteString);
		deleteButton.addActionListener(e -> createDeleteButton());
		upButton = new JButton(upString);
		upButton.setToolTipText("Move the currently selected list item higher.");
		upButton.addActionListener(e -> createUpDownButton(e));
		downButton = new JButton(downString);
		downButton.setToolTipText("Move the currently selected list item lower.");
		downButton.addActionListener(e -> createUpDownButton(e));
		
		JPanel upDownPanel = new JPanel(new GridLayout(2, 1));
		upDownPanel.add(upButton);
		upDownPanel.add(downButton);
		nameField = new JTextField(15);
        nameField.addActionListener(e -> addButtonAction());
        String name = listModel.getElementAt(list.getSelectedIndex()).toString();
        nameField.setText(name);
		
		JPanel buttonPane = new JPanel();
		buttonPane.add(nameField);
		buttonPane.add(addButton);
		buttonPane.add(deleteButton);
		buttonPane.add(upDownPanel);
		add(buttonPane, BorderLayout.PAGE_START);
		add(listScrollPane, BorderLayout.CENTER);
	}

	public void chooseUpdateOrder() {
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                nameField.setText("");
            } else if (list.getSelectedIndices().length > 1) {
                deleteButton.setEnabled(true);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
                upButton.setEnabled(true);
                downButton.setEnabled(true);
                nameField.setText(list.getSelectedValue().toString());
            }
        }
	}

	private void createAddButton() {
		if (nameField.getText().equals("")) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		int index = list.getSelectedIndex();
		int size = listModel.getSize();
		if (index == -1 || (index+1 == size)) {
			listModel.addElement(nameField.getText());
			list.setSelectedIndex(size);
		} else {
			listModel.insertElementAt(nameField.getText(), index+1);
			list.setSelectedIndex(index+1);
		}
	}

	private void createDeleteButton() {
		ListSelectionModel lsm = list.getSelectionModel();
		int firstSelected = lsm.getMinSelectionIndex();
		int lastSelected = lsm.getMaxSelectionIndex();
		listModel.removeRange(firstSelected, lastSelected);
		int size = listModel.size();
		if (size == 0) {
			deleteButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		} else {
			if (firstSelected == listModel.getSize()) {
				firstSelected--;
			}
			list.setSelectedIndex(firstSelected);
		}
	}

	private void createUpDownButton(ActionEvent e) {
		int moveMe = list.getSelectedIndex();
		if (e.getActionCommand().equals(upString)) {
			if (moveMe != 0) {     
				swap(moveMe, moveMe-1);
				list.setSelectedIndex(moveMe-1);
				list.ensureIndexIsVisible(moveMe-1);
			}
		} else {
			if (moveMe != listModel.getSize()-1) {
				swap(moveMe, moveMe+1);
				list.setSelectedIndex(moveMe+1);
				list.ensureIndexIsVisible(moveMe+1);
			}
		}
        for (int i = 0; i < list.getModel().getSize(); i++) {
            String item = list.getModel().getElementAt(i);
            System.out.println("Item = " + item);
        }
	}

	private void addButtonAction() {
		if (nameField.getText().equals("")) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		int index = list.getSelectedIndex();
		int size = listModel.getSize();
		if (index == -1 || (index+1 == size)) {
			listModel.addElement(nameField.getText());
			list.setSelectedIndex(size);
		} else {
			listModel.insertElementAt(nameField.getText(), index+1);
			list.setSelectedIndex(index+1);
		}
	}

	private void swap(int a, int b) {
		String aObject = listModel.getElementAt(a);
		String bObject = listModel.getElementAt(b);
		listModel.set(a, bObject);
		listModel.set(b, aObject);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Hi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new GameLoopManager();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		newContentPane.setMinimumSize(new Dimension(newContentPane.getPreferredSize().width, 100));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

package no.ntnu.item.ttm4115.termassignment.chatdialog;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.clientframe.ClientFrame;
import no.ntnu.item.ttm4115.termassignment.clientframe.LogTable;
import no.ntnu.item.ttm4115.termassignment.clientframe.PlaceholderTextField;

public class ChatDialog extends Block {

	public final String SEND_MSG = "SEND";

	/** Swing GUI */
	private ClientFrame frame;
	/** Panel which contains the text field and the buttons */
	private JPanel controls;
	/* The buttons */
	private JButton send;
	/** Input field where the user enters his location */
	private JTextField message;
	/** Label that indicates the current state */
	private JLabel stateLabel;
	/**
	 * The log
	 * use {@link LogTable#addLogEntry(no.ntnu.item.ttm4115.termassignment.clientframe.LogTable.Direction, String)}
	 */
	private LogTable messages;

	/** Object for retrieving the requested address */
	private Object chatMessage = new Object(){
		public String toString() {

			return message.getText();
		}
	};

	/**
	 * Initialise and show the GUI. Should only be called *once* per user
	 * @param alias	Alias for this user
	 */
	public void show(String s) {
		frame = new ClientFrame(this,s+" chat");
		controls = new JPanel(new GridBagLayout());
		stateLabel = new JLabel("<html>Location: <i>unknown");

		GridBagConstraints c = new GridBagConstraints();

		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;

		message = new PlaceholderTextField("Type message");
		c.gridy = 0;
		controls.add(message, c);
		c.gridwidth = 1;

		send = frame.createEventButton(SEND_MSG, "Send message", chatMessage);
		c.gridy = 1;
		controls.add(send, c);

		frame.add(controls, BorderLayout.SOUTH);
		frame.add(stateLabel, BorderLayout.NORTH);
		messages = frame.addLogTable();

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Close the GUI. Once closed, it cannot be reliably re-opened.
	 */
	public void close() {
		if (frame != null) {
			frame.dispose();
		}
	}

	/**
	 * Add an incoming message to the log
	 * @param message	The message received
	 */
	public void displayMessage(String message) {
		messages.addLogEntry(LogTable.Direction.IN, message);
	}

}

package no.ntnu.item.ttm4115.termassignment.userclientgui;

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

/**
 * Block for the UserClientGUI. By default, the GUI contains a text field
 * where the user can give his location, and two buttons.
 * Students are encouraged to make changes to this class
 * if they want to add features for extra credit.
 */
public class UserClientGUI extends Block {

	/**
	 * Event that indicates that the user requests a taxi.
	 * This request is accompanied by the current location of the user.
	 * The current location is entered in {@link #address} and passed via {@link #taxiRequest}
	 */
	public final String REQUEST = "REQUEST";
	/**
	 * Event that indicates that the user is no longer
	 * interested in a taxi. Any standing requests must be cancelled.
	 */
	public final String CANCEL = "CANCEL";
	
	/** Swing GUI */
	private ClientFrame frame;
	/** Panel which contains the text field and the buttons */
	private JPanel controls;
	/* The buttons */
	private JButton request, cancel;
	/** Input field where the user enters his location */
	private JTextField address;
	/** Label that indicates the current state */
	private JLabel stateLabel;
	/**
	 * The log
	 * use {@link LogTable#addLogEntry(no.ntnu.item.ttm4115.termassignment.clientframe.LogTable.Direction, String)}
	 */
	private LogTable messages;
	
	/** Alias for this user */
	private String alias;
	
	/** Object for retrieving the requested address */
	private Object taxiRequest = new Object(){
		public String toString() {
			return address.getText();
		}
	};
	
	/**
	 * Initialise and show the GUI. Should only be called *once* per user
	 * @param alias	Alias for this user
	 */
	public void show(String alias) {
		this.alias = alias;
		frame = new ClientFrame(this, alias);
		controls = new JPanel(new GridBagLayout());
		stateLabel = new JLabel("<html>Location: <i>unknown");
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		
		address = new PlaceholderTextField("Type address…");
		c.gridy = 0;
		controls.add(address, c);
		c.gridwidth = 1;
		
		request = frame.createEventButton(REQUEST, "Request taxi", taxiRequest);
		c.gridy = 1;
		controls.add(request, c);
		
		cancel = frame.createEventButton(CANCEL, "Cancel");
		c.gridx = 1;
		controls.add(cancel, c);

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

	/**
	 * Display a location in the GUI
	 * @param location	String representation of the location
	 */
	public void displayLocation(String location) {
		stateLabel.setText("<html>Location: <b>"+location.replace("<", "&lt;").replace("&", "&amp;"));
	}
}
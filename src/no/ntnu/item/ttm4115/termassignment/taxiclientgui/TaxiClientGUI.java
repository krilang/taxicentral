package no.ntnu.item.ttm4115.termassignment.taxiclientgui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.clientframe.ClientFrame;
import no.ntnu.item.ttm4115.termassignment.clientframe.LogTable;

/**
 * Block for the TaxiClientGUI. By default, the GUI contains a big log component,
 * and some buttons at the side. Students are encouraged to make changes to this class
 * if they want to add features for extra credit.
 */
public class TaxiClientGUI extends Block {

	/**
	 * Event that indicates that the taxi is On Duty
	 * which means that it is listening for
	 * orders via MQTT
	 */
	public final String ON_DUTY = "ON_DUTY";
	/**
	 * Event that indicates that the taxi is Off Duty
	 * which means that it is NOT listening for
	 * orders via MQTT
	 */
	public final String OFF_DUTY = "OFF_DUTY";
	/**
	 * Event that indicates that the taxi is available
	 * which means that it will respond to external orders
	 * and should be allocated to a user if one is waiting
	 */
	public final String AVAILABLE = "AVAILABLE";
	/**
	 * Event that indicates that the taxi is UNavailable
	 * which means that it should not be allocated to waiting users,
	 * but it will update its position on the map
	 */
	public final String UNAVAILABLE = "UNAVAILABLE";
	/**
	 * Event that indicates that the taxi knows about its order
	 * and that it accepts it
	 */
	public final String CONFIRM = "CONFIRM";
	
	/** Swing GUI */
	private ClientFrame frame;
	/** Panel which contains the buttons */
	private JPanel buttons;
	/* The buttons */
	private JButton onDuty, offDuty, available, unavailable, confirm;
	/**
	 * The log
	 * use {@link LogTable#addLogEntry(no.ntnu.item.ttm4115.termassignment.clientframe.LogTable.Direction, String)}
	 */
	private LogTable messages;
	
	/**
	 * Initialise and show the GUI. Should only be called *once* per taxi
	 * @param alias	Alias for this taxi
	 */
	public void show(String alias) {
		frame = new ClientFrame(this, alias);
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		
		onDuty = frame.createEventButton(ON_DUTY, "On duty");
		offDuty = frame.createEventButton(OFF_DUTY, "Off duty");
		available = frame.createEventButton(AVAILABLE, "Available");
		unavailable = frame.createEventButton(UNAVAILABLE, "Unavailable");
		confirm = frame.createEventButton(CONFIRM, "Confirm");
		ClientFrame.growButton(confirm);
		
		buttons.add(onDuty);
		buttons.add(offDuty);
		buttons.add(available);		
		buttons.add(unavailable);
		buttons.add(confirm);

		messages = frame.addLogTable();
		frame.add(buttons, BorderLayout.WEST);
		
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

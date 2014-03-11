package no.ntnu.item.ttm4115.termassignment.clientframe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class LogTable extends JTable {

	public enum Direction {IN, OUT}

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane = null;

	public LogTable() {
		super(new DefaultTableModel(new Object[0][0], new String[]{"Time", "in/out", "Message"}));
		getColumnModel().getColumn(0).setMaxWidth(SwingUtilities.computeStringWidth(getFontMetrics(getFont()), "MM:MM:MM"));
		getColumnModel().getColumn(1).setMaxWidth(SwingUtilities.computeStringWidth(getFontMetrics(getFont()), "IN/OUT"));
	}
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel) super.getModel();
	}
	public void setModel(TableModel model) throws IllegalArgumentException {
		if (model instanceof DefaultTableModel) {
			super.setModel(model);
		} else {
			throw new IllegalArgumentException("Requires DefaultTableModel");
		}
	}
	
	public void addLogEntry(Direction direction, String message) {
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		getModel().addRow(new Object[]{
				sdf.format(cal.getTime()),
				direction,
				message
		});
		if (scrollPane != null) {
			final JScrollPane scrollPane = this.scrollPane;
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					JScrollBar vertical = scrollPane.getVerticalScrollBar();
					vertical.setValue(vertical.getMaximum());
				}});
		}
	}

	public void setScrollPane(JScrollPane scrollPane) {
		if (scrollPane != null) {
			this.scrollPane = scrollPane;
		}
	}

}

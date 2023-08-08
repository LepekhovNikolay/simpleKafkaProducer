package simplekafkaproducer.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class UiUtils {

	public static String getTopic(ActionEvent event) {
		return (String) JComboBox.class.cast(event.getSource()).getSelectedItem();
	}

	public static List<String> getHeadersAsList(JList<String> headerUi) {
		var model = headerUi.getModel();
		List<String> list = new ArrayList<>(model.getSize());
		for (int i = 0; i < model.getSize(); i++) {
			list.add(model.getElementAt(i));
		}
		return list;
	}
}

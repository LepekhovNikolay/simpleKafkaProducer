package simplekafkaproducer.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import simplekafkaproducer.repository.Repository;
import simplekafkaproducer.util.TimeUtils;
import simplekafkaproducer.util.UiUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class MainFrame extends javax.swing.JFrame {

	public static final String HELP;

	static {
		try {
			HELP = IOUtils.toString(MainFrame.class.getClassLoader().getResourceAsStream("help.txt"), Charset.defaultCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private final Repository repository = new Repository();

	public MainFrame() {
		initComponents();
		addMenu();
	}

	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu mainMen = new JMenu("Main");
		menuBar.add(mainMen);
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.addActionListener(event -> {
			JFrame jFrame = new JFrame();
			JOptionPane.showMessageDialog(jFrame, HELP);
		});
		mainMen.add(helpItem);
	}

	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {


		serverAddressLabel = new javax.swing.JLabel();
		ServerAddressField = new javax.swing.JTextField();
		headersLabel = new javax.swing.JLabel();
		headersField = new javax.swing.JScrollPane();
		topicLabel = new javax.swing.JLabel();
		headersList = new javax.swing.JList<>();
		headersList.setModel(new DefaultListModel<String>());


		topicLabel.setText("Topic:");
		topicField = new JComboBox<>();
		topicField.addActionListener(event -> {
			String message = repository.getMessage(UiUtils.getTopic(event));
			if (messageField != null && StringUtils.isNotBlank(message)) {
				messageField.setText(message);
			}
			Collection<String> headers = repository.getHeaders(UiUtils.getTopic(event));
			if (headers != null && headersList.getModel() instanceof DefaultListModel) {
				var headersListModel = ((DefaultListModel<String>) headersList.getModel());
				headersListModel.removeAllElements();
			}
		});
		topicField.setEditable(true);
		repository.getTopics().forEach(topic -> {
			topicField.addItem(topic);
		});
		messageLabel = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		messageField = new javax.swing.JTextArea();
		messageField.setText(repository.getMessage((String) topicField.getSelectedItem()));
		sendButton = new javax.swing.JButton();
		prettifyButton = new javax.swing.JButton();
		removeTopicButton = new javax.swing.JButton();
		addHeaderButton = new javax.swing.JButton();
		removeHeaderButton = new javax.swing.JButton();
		messageKeyLabel = new javax.swing.JLabel();
		messageKeyTextField = new javax.swing.JTextField(UUID.randomUUID().toString());

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Simple Kafka producer");

		serverAddressLabel.setText("Server address:");

		ServerAddressField.setText(repository.getServerAddress());

		headersLabel.setText("Headers:");

		Collection<String> headers = repository.getHeaders((String) topicField.getSelectedItem());
		if (headers != null) {
			headers.forEach(header -> {
				var headersListModel = ((DefaultListModel<String>) headersList.getModel());
				headersListModel.add(headersListModel.size(), header);
			});
		}
		headersField.setViewportView(headersList);

		messageLabel.setText("Message:");

		messageField.setColumns(20);
		messageField.setRows(5);
		jScrollPane2.setViewportView(messageField);

		prettifyButton.setText("Prettify");
		prettifyButton.addActionListener(this::prettifyButtonAction);

		sendButton.setText("Send");
		sendButton.addActionListener(this::sendButtonAction);

		removeTopicButton.setText("-");
		removeTopicButton.addActionListener(e -> {
			if (topicField.getSelectedItem() != null) {
				topicField.removeItemAt(topicField.getSelectedIndex());
				repository.removeCurrentTopic((String) topicField.getSelectedItem());
			}
		});

		addHeaderButton.setText("+");
		addHeaderButton.addActionListener(this::addHeaderAction);

		removeHeaderButton.setText("-");
		removeHeaderButton.addActionListener(this::removeHeaderAction);

		messageKeyLabel.setText("Key:");

		draw();
	}// </editor-fold>//GEN-END:initComponents


	/**
	 * this method draw all elements and set their relations
	 */
	protected void draw() {

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane2)
										.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addComponent(topicLabel)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(topicField)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
														.addComponent(removeTopicButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
										.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addComponent(messageKeyLabel)
												.addGap(21, 21, 21)
												.addComponent(messageKeyTextField))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addGap(0, 0, Short.MAX_VALUE)
												.addComponent(prettifyButton)
												.addComponent(sendButton))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addComponent(headersField, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(addHeaderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(removeHeaderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
												))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(serverAddressLabel)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(ServerAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(messageLabel)
														.addComponent(headersLabel))
												.addGap(0, 0, Short.MAX_VALUE)))
								.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(serverAddressLabel)
										.addComponent(ServerAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(topicLabel)
										.addComponent(topicField, javax.swing.GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(removeTopicButton, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(messageKeyLabel)
										.addComponent(messageKeyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addComponent(headersLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(addHeaderButton)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(removeHeaderButton))
										.addComponent(headersField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addComponent(messageLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(sendButton)
										.addComponent(prettifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);

		pack();
	}

	@SneakyThrows
	private void prettifyButtonAction(java.awt.event.ActionEvent evt) {
		if (StringUtils.isEmpty(messageField.getText()))
			return;
		try {
			Gson gson = new GsonBuilder()
					.setPrettyPrinting()
					.create();
			JsonElement je = JsonParser.parseString(messageField.getText());
			String prettyJsonString = gson.toJson(je);
			messageField.setText(prettyJsonString);
			System.out.println(1);
		} catch (Exception e) {
			new Thread(() -> {
				prettifyButton.setBackground(Color.RED);
				TimeUtils.sleep(1, TimeUnit.SECONDS);
				prettifyButton.setBackground(Color.WHITE);
			}).start();
		}
	}

	private void sendButtonAction(java.awt.event.ActionEvent evt) {
		if (topicField.getSelectedItem() == null)
			return;
		repository.updateTopicSettings(
				topicField.getSelectedItem().toString(),
				messageField.getText(),
				UiUtils.getHeadersAsList(headersList));
		if (ServerAddressField.getText().isEmpty() || (
				topicField.getSelectedItem() != null &&
						topicField.getSelectedItem().toString().isEmpty() || messageKeyTextField.getText().isEmpty())) {
			JOptionPane.showMessageDialog(this, "Required fields are missing!", "Error", ERROR_MESSAGE);
			return;
		}

		Properties config = new Properties();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ServerAddressField.getText());
/*    config.put("sasl.mechanism", "SCRAM-SHA-512");
    config.put("security.protocol", "SASL_PLAINTEXT");
    String SASL_JAAS_CONFIG_PATTERN = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    Object userName = ((Map<?,?>)PROPS.get("user")).get("name");
    Object userPassword = ((Map<?,?>)PROPS.get("user")).get("password");
    config.put("sasl.jaas.config", String.format(SASL_JAAS_CONFIG_PATTERN, userName, userPassword));*/
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(config);

//    Properties consumerProps = new Properties();
//    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, jTextField1.getText());
//    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test123");
//    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProps);

		String topic = topicField.getSelectedItem().toString().trim();
		String key = messageKeyTextField.getText().trim();
		String value = messageField.getText().trim();

		ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

		Object[] headerObjects = ((DefaultListModel<String>) headersList.getModel()).toArray();
		for (Object headerString : headerObjects) {
			String[] headerStringParts = headerString.toString().split(":");
			RecordHeader header = new RecordHeader(headerStringParts[0], headerStringParts[1].trim().getBytes(Charset.defaultCharset()));
			record.headers().add(header);
		}

		try {
			RecordMetadata metaData = kafkaProducer.send(record).get();
			System.out.println(metaData);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}//GEN-LAST:event_jButton1ActionPerformed

	private void addHeaderAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		NewHeaderDialog newHeaderDialog = new NewHeaderDialog(this, true);
		newHeaderDialog.setLocationRelativeTo(this);
		newHeaderDialog.setVisible(true);

		if (newHeaderDialog.isOkPressed()) {
			String headerString = newHeaderDialog.getHeaderName() + ": " + newHeaderDialog.getHeaderValue();
			((DefaultListModel<String>) headersList.getModel()).addElement(headerString);
		}
	}//GEN-LAST:event_jButton2ActionPerformed

	private void removeHeaderAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
		if (headersList.getSelectedIndex() < 0) return;
		((DefaultListModel<String>) headersList.getModel()).remove(headersList.getSelectedIndex());
	}//GEN-LAST:event_jButton3ActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton sendButton;
	private javax.swing.JButton prettifyButton;
	private javax.swing.JButton addHeaderButton;
	private javax.swing.JButton removeHeaderButton;
	private javax.swing.JLabel serverAddressLabel;
	private javax.swing.JLabel headersLabel;
	private javax.swing.JLabel messageLabel;
	private javax.swing.JLabel messageKeyLabel;
	private javax.swing.JList<String> headersList;
	private javax.swing.JScrollPane headersField;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea messageField;
	private javax.swing.JTextField ServerAddressField;
	private javax.swing.JTextField messageKeyTextField;
	private javax.swing.JComboBox<String> topicField;
	private javax.swing.JLabel topicLabel;
	private javax.swing.JButton removeTopicButton;
	// End of variables declaration//GEN-END:variables
}


package simplekafkaproducer.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import simplekafkaproducer.ui.dto.Setting;
import simplekafkaproducer.ui.dto.Topic;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Repository {

	private Setting properties;

	public Repository() {
		try {
			downloadProps();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getServerAddress() {
		return properties.getServerAddress();
	}

	public Collection<String> getTopics() {
		return properties.getTopics().stream()
				.map(Topic::getName)
				.collect(Collectors.toList());
	}

	public String getMessage(String topic) {
		return properties.getTopics().stream()
				.filter(t -> t.getName().equals(topic))
				.map(Topic::getMessage)
				.findAny()
				.orElse("");

	}

	public Collection<String> getHeaders(String topic) {
		return properties.getTopics().stream()
				.filter(t -> t.getName().equals(topic))
				.filter(t -> t.getHeaders() != null)
				.map(Topic::getHeaders)
				.findAny()
				.orElse(new ArrayList<>());
	}

	private void downloadProps() throws IOException {
		Yaml yaml = new Yaml(new CustomClassLoaderConstructor(
				Setting.class,
				ClassLoader.getSystemClassLoader(),
				new LoaderOptions()));
		properties = yaml.load(Files.newInputStream(Paths.get("settings.yaml")));
	}

	public void updateTopicSettings(String topicName,
									String message,
									List<String> headers) {
		properties.getTopics().stream()
				.filter(t -> t.getName().equals(topicName))
				.findAny()
				.ifPresentOrElse(t -> {
					t.setMessage(message);
					t.setHeaders(headers);
				}, () -> properties.getTopics().add(new Topic(topicName, message, headers)));
		flushProperties();
	}

	public void removeCurrentTopic(String topicName) {
		properties.getTopics().stream()
				.filter(topic -> topic.getName().equals(topicName))
				.findAny()
				.ifPresent(topicToRemove -> {
					properties.getTopics().remove(topicToRemove);
					flushProperties();
				});
	}

	private void flushProperties() {
		try {
			Yaml yaml = new Yaml();
			FileWriter fileWriter = new FileWriter("settings.yaml");
			Object propertiesAsMap = new ObjectMapper().convertValue(properties, Map.class);
			yaml.dump(propertiesAsMap, fileWriter);
			fileWriter.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}

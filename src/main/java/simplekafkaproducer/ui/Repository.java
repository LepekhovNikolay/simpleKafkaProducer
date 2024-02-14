package simplekafkaproducer.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import simplekafkaproducer.ui.dto.Setting;
import simplekafkaproducer.ui.dto.Topic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        return properties.getTopics().stream().map(Topic::getName).collect(Collectors.toList());
    }

    public String getMessage(String topic) {
        return properties.getTopics().stream().filter(t -> t.getName().equals(topic)).map(Topic::getMessage).findAny().orElse("");

    }

    public Collection<String> getHeaders(String topic) {
        return properties.getTopics().stream().filter(t -> t.getName().equals(topic)).filter(t -> t.getHeaders() != null).map(Topic::getHeaders).findAny().orElse(new ArrayList<>());
    }

    private void downloadProps() throws IOException {
        File settingsAsFile = new File("settings.json");
        if (!settingsAsFile.exists()) {
            settingsAsFile.createNewFile();
            Files.writeString(settingsAsFile.toPath(), "{}");
        }
        properties = new ObjectMapper().readValue(settingsAsFile, Setting.class);

    }

    public void updateTopicSettings(String topicName,
                                    String message,
                                    List<String> headers) {
        properties.getTopics().stream().filter(t -> t.getName().equals(topicName)).findAny().ifPresentOrElse(t -> {
            t.setMessage(message);
            t.setHeaders(headers);
        }, () -> {
            properties.getTopics().add(new Topic(topicName, message, headers));
        });
        flushProperties();
    }

    public void removeCurrentTopic(String topicName) {
        properties.getTopics().stream().filter(topic -> topic.getName().equals(topicName)).findAny().ifPresent(topicToRemove -> {
            properties.getTopics().remove(topicToRemove);
            flushProperties();
        });
    }

    private void flushProperties() {
        try(PrintWriter pw = new PrintWriter(new FileWriter("settings.json"))) {
            String propertiesAsString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(properties);
            pw.write(propertiesAsString);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

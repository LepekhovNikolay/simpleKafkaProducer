package simplekafkaproducer.ui.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
public class Setting {
	private String serverAddress;
	private Set<Topic> topics;

	public String getServerAddress() {
		if (StringUtils.isBlank(serverAddress))
			serverAddress = "localhost:9092";
		return serverAddress;
	}

	public Set<Topic> getTopics() {
		if (topics == null)
			topics = new HashSet<>();
		return topics;
	}
}



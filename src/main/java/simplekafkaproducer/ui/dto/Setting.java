package simplekafkaproducer.ui.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Setting {
	private String serverAddress;
	private Set<Topic> topics;
}

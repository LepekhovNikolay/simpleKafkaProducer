package simplekafkaproducer.ui.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Topic {
	private String name;
	private String message;
	private Collection<String> headers;
}

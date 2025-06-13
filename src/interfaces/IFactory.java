package interfaces;

import com.fasterxml.jackson.databind.JsonNode;

public interface IFactory {
    Object create(JsonNode node);
}

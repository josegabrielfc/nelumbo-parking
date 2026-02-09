package nelumbo.api.parking.infrastructure.adapter.out.persistence.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(FORMATTER) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            if (s.matches("^\\d+$")) {
                long timestamp = Long.parseLong(s);
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        ZoneId.systemDefault());
            }
            return LocalDateTime.parse(s, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}

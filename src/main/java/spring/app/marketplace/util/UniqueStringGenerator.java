package spring.app.marketplace.util;

import spring.app.marketplace.models.Good;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.models.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UniqueStringGenerator {

    public static String generateUniqueString(List<Good> goods, Person owner, LocalDateTime created_at, int id) {
        StringBuilder stringBuilder = new StringBuilder();

        // Добавляем идентификаторы товаров
        String goodsIds = goods.stream()
                .map(good -> String.valueOf(good.getId()))
                .collect(Collectors.joining(","));
        stringBuilder.append(goodsIds);

        // Добавляем идентификатор владельца
        stringBuilder.append(owner.getId());

        // Добавляем дату и время создания в формате "yyyyMMddHHmmss"
        String createdAtString = created_at.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        stringBuilder.append(createdAtString);

        // Добавляем идентификатор
        stringBuilder.append(id);

        // Преобразуем полученную строку в верхний регистр
        String uniqueString = stringBuilder.toString().toUpperCase();

        // Генерируем уникальную строку состоящую только из букв
        String uuid = UUID.nameUUIDFromBytes(uniqueString.getBytes()).toString();
        return uuid.replaceAll("-", ""); // Удаляем дефисы
    }
}

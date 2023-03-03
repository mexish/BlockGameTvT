package me.mexish.blockgametvt.type;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author mexish
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode
@Data
public final class PlayerProcessingRecord {

    Future<?> executionId;

}

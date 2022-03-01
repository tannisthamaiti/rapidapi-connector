package org.mule.extension.rapidapi.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parse {
	private Parse(){ }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String inputStreamToString(InputStream inputStream) throws IOException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            return outputStream.toString("UTF-8");
        }
    }

    public static HttpEntity objectToEntity(Object object) throws JsonProcessingException {

        String jsonAsString = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);

        return  new ByteArrayHttpEntity(jsonAsString.getBytes(StandardCharsets.UTF_8));
    }

    public static String cleanMuleFormat(String jsonString) {
        Pattern pattern = Pattern.compile("\\#\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(jsonString);
        matcher.find();
        return matcher.group(1);
    }


}

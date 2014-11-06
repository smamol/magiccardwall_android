package nz.co.trademe.fedex5.magiccardwall.api.converter;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * @author pgirardi - pedro.girardi@trademe.co.nz
 */
public class JacksonConverter implements Converter {

    private final ObjectMapper objectMapper;

    public JacksonConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(new UpperCaseNamingStrategy());
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        JavaType javaType = objectMapper.getTypeFactory().constructType(type);

        try {
            return objectMapper.readValue(body.in(), javaType);
        } catch (IOException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(objectMapper.writeValueAsBytes(object));
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize Java object.", e);
        }
    }

    private static final class JsonTypedOutput implements TypedOutput {

        private final byte[] mJsonBytes;
        private final String mMimeType;

        public JsonTypedOutput(byte[] jsonBytes) {
            mJsonBytes = jsonBytes;
            mMimeType = "application/json; charset=UTF-8";
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mMimeType;
        }

        @Override
        public long length() {
            return mJsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(mJsonBytes);
        }
    }

    private static final class UpperCaseNamingStrategy extends PropertyNamingStrategy {

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return this.translateName(defaultName);
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            // UpperCaseNamingStrategy is ignoring any annotations and changes all fields first letter to uppercase.
            // The api doesn't accept this in two cases, "comment" and "answer"
            if (method != null && method.getAnnotation(org.codehaus.jackson.annotate.JsonProperty.class) != null) {
                String annotatedValue = method.getAnnotation(org.codehaus.jackson.annotate.JsonProperty.class).value();
                if (annotatedValue != null && annotatedValue.length() > 0) {
                    return annotatedValue;
                }
            }

            return this.translateName(defaultName);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return this.translateName(defaultName);
        }

        public String translateName(String name) {
            char newFirstChar = Character.toUpperCase(name.charAt(0));
            return newFirstChar + name.substring(1);
        }

    }

}

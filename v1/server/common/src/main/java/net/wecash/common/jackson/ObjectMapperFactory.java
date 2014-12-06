package net.wecash.common.jackson;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.bson.types.ObjectId;
/**
 * @author franklin.li
 */
public class ObjectMapperFactory {
    private ObjectMapper mapper;

    public ObjectMapperFactory() {
        super();
        this.mapper = new ObjectMapper();
        this.mapper.writerWithDefaultPrettyPrinter();
        SimpleModule module = new SimpleModule("ObjectIdModule");
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        this.mapper.registerModule(module);
        this.mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//		this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.setSerializationInclusion(Include.NON_NULL);
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}

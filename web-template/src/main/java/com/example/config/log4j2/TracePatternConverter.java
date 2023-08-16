package com.example.config.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.slf4j.MDC;

/**
 * 自定义 log4j2 的 pattern 关键字
 * 需要在 log4j2.xml 文件的 configuration 标签中加入属性 package="com.example.config.log4j2" 才会生效
 * @author WuQinglong
 * @since 2023/8/8 10:14
 */
@Plugin(name = "TracePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"dltag"})
public class TracePatternConverter extends LogEventPatternConverter {

    private static final TracePatternConverter INSTANCE = new TracePatternConverter("dltag", "dltag");

    public static TracePatternConverter newInstance() {
        return INSTANCE;
    }

    public TracePatternConverter(String name, String style) {
        super(name, style);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        String dltag = MDC.get("dltag");
        if (dltag != null) {
            stringBuilder.append(dltag);
            return;
        }
        stringBuilder.append("_undef");
    }
}

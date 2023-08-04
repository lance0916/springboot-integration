//package com.example.config.converter;
//
//import ch.qos.logback.classic.pattern.ClassicConverter;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import com.example.bean.constants.TraceConstant;
//import java.util.Map;
//
///**
// * logback日志格式转换类
// */
//public class LogbackTraceConverter extends ClassicConverter {
//
//    private final static String EMPTY = "";
//
//    @Override
//    public String convert(ILoggingEvent loggingEvent) {
//        Map<String, String> map = loggingEvent.getMDCPropertyMap();
//        String traceId = map.getOrDefault(TraceConstant.TRACE_ID, EMPTY);
//        String spanId = map.getOrDefault(TraceConstant.SPAN_ID, EMPTY);
//        // cspanId 是传给下游的，能打印的只有在 RPC 日志中
//        String cspanId = map.getOrDefault(TraceConstant.CSPAN_ID, EMPTY);
//
//        String msg = loggingEvent.getFormattedMessage();
//        if (msg != null && msg.startsWith("_")) {
//            return msg;
//        }
//        return msg + "todo";
//    }
//}

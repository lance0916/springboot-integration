package com.snailwu.example;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import org.treesitter.TSNode;
import org.treesitter.TSParser;
import org.treesitter.TSPoint;
import org.treesitter.TSQuery;
import org.treesitter.TSQueryCapture;
import org.treesitter.TSQueryCursor;
import org.treesitter.TSQueryMatch;
import org.treesitter.TSTree;
import org.treesitter.TreeSitterJava;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author WuQinglong
 * @date 2024/3/22 13:41
 */
public class TreeSitterMain {

    public static void main(String[] args) {
        TSParser parser = new TSParser();
        parser.setLanguage(new TreeSitterJava());

//        cutError(parser, "demo-error-01.txt");
//        cutError(parser, "demo-error-02.txt");
//        cutMissing(parser, "demo-missing-01.txt");
//        cutMissing(parser, "demo-missing-02.txt");
        cutMissing(parser, "demo-missing-03.txt");
    }

    public static void cutMissing(TSParser parser, String fileName) {
        String code = loadFileText(fileName);
        TSTree tree = parser.parseString(null, code);
        TSNode rootNode = tree.getRootNode();

        /*
            (class_declaration) @missing
            (method_declaration) @missing
            (local_variable_declaration) @missing
            (expression_statement) @missing
            (if_statement) @missing
            (while_statement) @missing
            (for_statement) @missing
            (return_statement) @missing
         */
        TSQuery query = new TSQuery(new TreeSitterJava(), "(expression_statement) @missing");
        TSQueryCursor cursor = new TSQueryCursor();
        cursor.exec(query, rootNode);

        TSQueryMatch match = new TSQueryMatch();
        while (cursor.nextMatch(match)) {
            for (TSQueryCapture capture : match.getCaptures()) {
                TSNode node = capture.getNode();
                code = cutText(code, node.getStartPoint(), node.getEndPoint());
            }
        }
        System.out.println("cut missing: [" + fileName + "] begin");
        System.out.println(code);
        System.out.println("cut missing: [" + fileName + "] end");
    }

    public static void cutError(TSParser parser, String fileName) {
        String code = loadFileText(fileName);
        TSTree tree = parser.parseString(null, code);
        TSNode rootNode = tree.getRootNode();

        TSQuery query = new TSQuery(new TreeSitterJava(), "(ERROR) @error");
        TSQueryCursor cursor = new TSQueryCursor();
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();
        while (cursor.nextMatch(match)) {
            for (TSQueryCapture capture : match.getCaptures()) {
                TSNode node = capture.getNode();
                code = cutText(code, node.getStartPoint(), node.getEndPoint());
            }
        }
        System.out.println("cut error: [" + fileName + "] begin");
        System.out.println(code);
        System.out.println("cut error: [" + fileName + "] end");
    }

    public static String cutText(String text, TSPoint start, TSPoint end) {
        List<String> lines = StrUtil.split(text, "\n");
        int startLine = start.getRow();
        int endLine = end.getRow();
        int startColumn = start.getColumn();
        int endColumn = end.getColumn();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (i == startLine) {
                sb.append(line, 0, startColumn);
            } else if (i == endLine) {
                sb.append(line, endColumn, line.length());
            } else if (i > startColumn && i < endLine) {
                continue;
            } else {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static String loadFileText(String filename) {
        ClassPathResource resource = new ClassPathResource(filename);
        return IoUtil.read(resource.getStream(), StandardCharsets.UTF_8);
    }

}

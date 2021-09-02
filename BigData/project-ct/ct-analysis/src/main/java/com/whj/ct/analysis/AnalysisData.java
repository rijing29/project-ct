package com.whj.ct.analysis;

import com.whj.ct.analysis.tool.AnalysisBeanTool;
import com.whj.ct.analysis.tool.AnalysisTextTool;
import org.apache.hadoop.util.ToolRunner;

public class AnalysisData {
    public static void main(String[] args) throws Exception {
        int result = ToolRunner.run(new AnalysisBeanTool(), args);
    }
}

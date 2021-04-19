package cn.arning.jgit.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author arning
 */
public class ExcelDataListener extends AnalysisEventListener<ExcelData> {
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println("1");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

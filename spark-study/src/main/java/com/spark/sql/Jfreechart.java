package com.spark.sql;

import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.Test;

/*
public class Jfreechart {
	@Test
	public void pie() throws Exception {
		File f = new File("d:/pie.png");

		//数据集
		DefaultPieDataset ds = new DefaultPieDataset();
		ds.setValue("HuaWei",3000);
		ds.setValue("Apple",5000);
		ds.setValue("Mi",1890);

		JFreeChart chart = ChartFactory.createPieChart("饼图演示", ds, false, false, false);

		Font font = new Font("宋体",Font.BOLD,15);
		chart.getTitle().setFont(font);
		//背景透明

		((PiePlot)chart.getPlot()).setForegroundAlpha(0.2f);
		((PiePlot)chart.getPlot()).setExplodePercent("Apple",0.1f);
		((PiePlot)chart.getPlot()).setExplodePercent("HuaWei",0.2f);
		((PiePlot)chart.getPlot()).setExplodePercent("Mi",0.3f);

		
		//创建3D饼图
		ChartUtils.saveChartAsJPEG(f, chart,400,300);
	}
}	
*/

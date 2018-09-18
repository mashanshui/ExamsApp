package com.shenhesoft.examsapp.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.allen.library.SuperTextView;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.MapTableData;
import com.bin.david.form.utils.DensityUtils;
import com.google.gson.Gson;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.StatisticsModel;
import com.shenhesoft.examsapp.present.StatisticsPresent;
import com.shenhesoft.examsapp.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 个人中心->学习统计
 */
public class StatisticsActivity extends XTitleActivity<StatisticsPresent> {

    @BindView(R.id.tv_statistics_period)
    SuperTextView tvStatisticsPeriod;
    @BindView(R.id.table)
    SmartTable table;

    //X轴的标注
    String[] Xdate = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    //Y轴的标注
    String[] Ydate = {"0%", "10%", "30%", "50%", "70%", "80%", "90&", "100%"};
    int[] Yvalues = {0, 10, 30, 50, 70, 80, 90, 100};

    @BindView(R.id.chart_number)
    ColumnChartView chartNumber;
    @BindView(R.id.chart_rate)
    LineChartView chartRate;
    private List<PointValue> mRatePointValues = new ArrayList<PointValue>();
    private List<AxisValue> mRateAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue> mRateAxisYValues = new ArrayList<AxisValue>();
    private List<StatisticsModel> statisticsModelList;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("学习统计记录");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().loadData();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 15));

        TableConfig tableConfig = table.getConfig();
        tableConfig.setShowTableTitle(false);
        tableConfig.setShowXSequence(false);
        tableConfig.setShowYSequence(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    public StatisticsPresent newP() {
        return new StatisticsPresent();
    }

    @OnClick(R.id.tv_statistics_period)
    public void onViewClicked() {
    }

    private void updateChart() {
        //定义有多少个柱子
        int numColumns = 12;
        //Column 是下图中柱子的实现类
        List<Column> columns = new ArrayList<>();
        float subValues1 = 0;
        float subValues2 = 0;
        float subValues3 = 0;
        StatisticsModel statisticsModel1 = statisticsModelList.get(0);
        StatisticsModel statisticsModel2 = statisticsModelList.get(1);
        StatisticsModel statisticsModel3 = statisticsModelList.get(2);
        //循环初始化每根柱子
        for (int i = 0; i < numColumns; i++) {
            switch (i) {
                case 0:
                    subValues1 = statisticsModel1.getB_oneMonth();
                    subValues2 = statisticsModel2.getB_oneMonth();
                    subValues3 = statisticsModel3.getB_oneMonth();
                    break;
                case 1:
                    subValues1 = statisticsModel1.getC_twoMonth();
                    subValues2 = statisticsModel2.getC_twoMonth();
                    subValues3 = statisticsModel3.getC_twoMonth();
                    break;
                case 2:
                    subValues1 = statisticsModel1.getD_threeMonth();
                    subValues2 = statisticsModel2.getD_threeMonth();
                    subValues3 = statisticsModel3.getD_threeMonth();
                    break;
                case 3:
                    subValues1 = statisticsModel1.getE_fourMonth();
                    subValues2 = statisticsModel2.getE_fourMonth();
                    subValues3 = statisticsModel3.getE_fourMonth();
                    break;
                case 4:
                    subValues1 = statisticsModel1.getF_fiveMonth();
                    subValues2 = statisticsModel2.getF_fiveMonth();
                    subValues3 = statisticsModel3.getF_fiveMonth();
                    break;
                case 5:
                    subValues1 = statisticsModel1.getG_sixMonth();
                    subValues2 = statisticsModel2.getG_sixMonth();
                    subValues3 = statisticsModel3.getG_sixMonth();
                    break;
                case 6:
                    subValues1 = statisticsModel1.getH_sevenMonth();
                    subValues2 = statisticsModel2.getH_sevenMonth();
                    subValues3 = statisticsModel3.getH_sevenMonth();
                    break;
                case 7:
                    subValues1 = statisticsModel1.getJ_eightMonth();
                    subValues2 = statisticsModel2.getJ_eightMonth();
                    subValues3 = statisticsModel3.getJ_eightMonth();
                    break;
                case 8:
                    subValues1 = statisticsModel1.getK_nineMonth();
                    subValues2 = statisticsModel2.getK_nineMonth();
                    subValues3 = statisticsModel3.getK_nineMonth();
                    break;
                case 9:
                    subValues1 = statisticsModel1.getL_tenMonth();
                    subValues2 = statisticsModel2.getL_tenMonth();
                    subValues3 = statisticsModel3.getL_tenMonth();
                    break;
                case 10:
                    subValues1 = statisticsModel1.getM_elevenMonth();
                    subValues2 = statisticsModel2.getM_elevenMonth();
                    subValues3 = statisticsModel3.getM_elevenMonth();
                    break;
                case 11:
                    subValues1 = statisticsModel1.getN_twelveMonth();
                    subValues2 = statisticsModel2.getN_twelveMonth();
                    subValues3 = statisticsModel3.getN_twelveMonth();
                    break;
                default:
                    break;
            }
            //SubcolumnValue 是下图中柱子中的小柱子的实现类
            List<SubcolumnValue> values = new ArrayList<>();
            //每一根柱子中有三根小柱子
            values.add(new SubcolumnValue(subValues1, Color.parseColor("#2EC8CA")));
            values.add(new SubcolumnValue(subValues2, Color.parseColor("#B6A2DD")));
            values.add(new SubcolumnValue(subValues3, Color.parseColor("#5BB0F0")));
            //初始化Column
            Column column = new Column(values);

            columns.add(column);
        }
        //定义表格实现类
        ColumnChartData columnChartData = new ColumnChartData(columns);
        Axis axisBootom = new Axis();
        Axis axisLeft = new Axis();

        List<AxisValue> axisValuess = new ArrayList<>();
        for (int i = 0; i < numColumns; i++) {
            axisValuess.add(new AxisValue(i).setLabel(++i + "月"));
        }
        axisBootom.setValues(axisValuess);
        columnChartData.setAxisXBottom(axisBootom);
        columnChartData.setAxisYLeft(axisLeft);

        //给画表格的View添加要画的表格
        chartNumber.setColumnChartData(columnChartData);
        //设置行为属性，支持缩放、滑动以及平移
        chartNumber.setInteractive(true);
        chartNumber.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        chartNumber.setMaxZoom((float) 3); //缩放比例

        Viewport v = new Viewport(chartNumber.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        chartNumber.setCurrentViewport(v);
    }

    private void updateTable() {
        Gson gson = new Gson();
        String json = gson.toJson(statisticsModelList, ArrayList.class);
        Log.e(TAG, "getData: " + json);
        MapTableData tableData = MapTableData.create("", JsonHelper.jsonToMapList(json));
        table.setTableData(tableData);
    }

    /**
     * X 轴的显示
     */
    protected void addAxisXLables() {
        if (!mRateAxisXValues.isEmpty()) {
            mRateAxisXValues.clear();
        }
        for (int i = 0; i < Xdate.length; i++) {
            mRateAxisXValues.add(new AxisValue(i).setLabel(Xdate[i]));
        }
    }

    /**
     * Y 轴的显示
     */
    protected void addAxisYLables() {
        if (!mRateAxisYValues.isEmpty()) {
            mRateAxisYValues.clear();
        }
        for (int i = 0; i < Ydate.length; i++) {
            mRateAxisYValues.add(new AxisValue(Yvalues[i]).setLabel(Ydate[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    protected void addAxisPoints() {
        if (!mRatePointValues.isEmpty()) {
            mRatePointValues.clear();
        }
        float value = 0;
        StatisticsModel statisticsModel = statisticsModelList.get(3);
        for (int i = 0; i < Xdate.length; i++) {
            switch (i) {
                case 0:
                    value = statisticsModel.getB_oneMonth();
                    break;
                case 1:
                    value = statisticsModel.getC_twoMonth();
                    break;
                case 2:
                    value = statisticsModel.getD_threeMonth();
                    break;
                case 3:
                    value = statisticsModel.getE_fourMonth();
                    break;
                case 4:
                    value = statisticsModel.getF_fiveMonth();
                    break;
                case 5:
                    value = statisticsModel.getG_sixMonth();
                    break;
                case 6:
                    value = statisticsModel.getH_sevenMonth();
                    break;
                case 7:
                    value = statisticsModel.getJ_eightMonth();
                    break;
                case 8:
                    value = statisticsModel.getK_nineMonth();
                    break;
                case 9:
                    value = statisticsModel.getL_tenMonth();
                    break;
                case 10:
                    value = statisticsModel.getM_elevenMonth();
                    break;
                case 11:
                    value = statisticsModel.getN_twelveMonth();
                    break;
                default:
                    break;
            }
            mRatePointValues.add(new PointValue(i, value));
        }
    }


    /**
     * 初始化LineChart的一些设置
     */
    protected void initLineChart() {
        Line line = new Line(mRatePointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
//	    line.setStrokeWidth(3);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setFormatter(new SimpleLineChartValueFormatter(1));
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色

//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(11);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mRateAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        Axis axisY = new Axis();  //Y轴
        axisY.setTextSize(11);//设置字体大小
        axisY.setHasLines(true);
        axisY.setValues(mRateAxisYValues);
//        axisY.setFormatter(new SimpleAxisValueFormatter());
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        chartRate.setInteractive(true);
        chartRate.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        chartRate.setMaxZoom((float) 3);//缩放比例
        chartRate.setLineChartData(data);
        chartRate.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(chartRate.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        chartRate.setCurrentViewport(v);
    }

    public void updateData(List<StatisticsModel> statisticsModelList) {
        this.statisticsModelList = statisticsModelList;
        //做题统计-表格
        updateTable();
        //做题统计
        updateChart();
        //做题正确率
        addAxisPoints();
        addAxisXLables();
        addAxisYLables();
        initLineChart();
    }

}

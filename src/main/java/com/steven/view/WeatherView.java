package com.steven.view;

import com.steven.bean.City;
import com.steven.bean.NowWeather;
import com.steven.spider.WeatherSpider;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherView {
    private static JFrame main_frame;
    private static JPanel top_pane;
    private static JPanel mid_pane;
    private static JPanel bottom_pane;
    private static JTextField search_textField;
    private static JComboBox<City> comboBox;
    private static SimpleDateFormat sdf;
    private static JLabel current_time;
    private static JButton sear_button;

    public static void main(String[] args) {
        try {
//            去除顶部设置按钮
            UIManager.put("RootPane.setupButtonVisible",false);
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            //TODO exception
        }
//        JFrame设置标题
        main_frame = new JFrame("MyWeather");
//        设置窗口不可以拖动大小
        main_frame.setResizable(false);
//        JFrame设置图标
        ImageIcon icon = new ImageIcon(WeatherView.class.getResource("/app_ico/logo.png"));
        main_frame.setIconImage(icon.getImage());

//        JFrame加载背景图片
        ImageIcon bg = new ImageIcon(WeatherView.class.getResource("/app_ico/bg.jpg"));
        JLabel bg_label = new JLabel(bg);
        bg_label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
//        把背景图片放入JFrame上
        main_frame.getLayeredPane().add(bg_label,new Integer(Integer.MIN_VALUE));

//        获取系统的分辨率
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        根据系统的分辨率设置窗口的大小
        main_frame.setSize(new Dimension((int) (screenSize.width * 0.6), (int) (screenSize.height * 0.6)));

//        把整个界面的布局分为上中下三块
//        顶部 提示搜索栏
//        ===========================================  顶栏  ===========================================
        top_pane = new JPanel();
//        关键字输入框
        search_textField = new JTextField(FlowLayout.CENTER);
        search_textField.setFont(new Font("楷体",Font.BOLD,25));
        search_textField.setColumns(10);
//        输入城市 标签
        JLabel input_city_name = new JLabel("  输入城市 ");
        input_city_name.setFont(new Font("楷体",Font.BOLD,30));

//        搜索结果下拉选项
        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("楷体",Font.BOLD,20));
        comboBox.addItem(new City("国家","省份","市区","县城","","",""));

//      设置当前时间
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current_time = new JLabel("年-月-日 时-分-秒",JLabel.LEFT);
        current_time.setFont(new Font("楷体",Font.BOLD,30));
        current_time.setForeground(new Color(238,87,87));

//      搜索按钮
        sear_button = new JButton("查询");
        sear_button.setBackground(new Color(36,212,126));
        sear_button.setForeground(new Color(36,212,126));
        sear_button.setFont(new Font("楷体",Font.BOLD,20));

//        将顶栏组件添加到 top_pane上
        top_pane.add(current_time);
        top_pane.add(input_city_name);
        top_pane.add(search_textField);
        top_pane.add(sear_button);
        top_pane.add(comboBox);

//        ===========================================  中栏  ===========================================
        mid_pane = new JPanel(new BorderLayout());
//        加载开场动画
        ImageIcon gif = new ImageIcon(WeatherView.class.getResource("/app_ico/play.gif"));
        gif.setImage(gif.getImage().getScaledInstance((int)(main_frame.getWidth()),(int)(main_frame.getHeight()),Image.SCALE_DEFAULT));
        JLabel label_gif = new JLabel(gif);
        mid_pane.add(label_gif);

//        ===========================================  尾栏  ===========================================
//        作者信息
        bottom_pane = new JPanel(new FlowLayout());
        bottom_pane.setBorder(new BevelBorder(1));
        JLabel author = new JLabel("Coding By Steven");
        author.setFont(new Font("楷体",Font.BOLD,25));
        author.setForeground(new Color(105,87,238));
        bottom_pane.add(author);

//        将上中下三个面板的背景设置为透明
        top_pane.setOpaque(false);
        mid_pane.setOpaque(false);
        bottom_pane.setOpaque(false);
//        将上中下三个面板添加到JFrame上
        main_frame.add(top_pane, BorderLayout.NORTH);
        main_frame.add(mid_pane, BorderLayout.CENTER);
        main_frame.add(bottom_pane, BorderLayout.SOUTH);
//      设置JFrame居中显示
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);

        new Thread(){
            @Override
            public void run() {
                while (true){
//                    不断刷新窗体,防止输入时阻塞
                    main_frame.repaint();
//                    显示当前的系统时间
                    current_time.setText(sdf.format(new Date()));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

//      设置窗口点击 X 时,正常关闭
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myEvent();
    }

    public static void myEvent(){
//        按下回车键开始搜索
        search_textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                e.VK 预定义的按键 VK_ 查看
                int press_key = e.getKeyCode();
                if (press_key == e.VK_ENTER){
                    start_search();
                }
            }
        });
//       点击按钮开始搜索
        sear_button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start_search();
            }
        });

//        通过下来选项来控制信息显示项
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getItemCount() > 0){
//                    当comboBox中有内容时,才处理,防止空指针
                    mid_pane.removeAll();
//                    获取当前选中项
                    City select_city = comboBox.getItemAt(comboBox.getSelectedIndex());
                    try {
//                        将当前选中城市的 经纬度传递到爬虫的参数里 , 获得当前天气对象
                        NowWeather nowWeather = new WeatherSpider().getNowWeather(select_city.getLon(), select_city.getLat());
//                        text 为天气
                        JLabel text = new JLabel(nowWeather.getText(),JLabel.CENTER);
                        text.setForeground(new Color(250,107,4));
                        text.setFont(new Font("楷体",Font.BOLD,100));
//                        icon 为天气的图标编号
                        ImageIcon icon = new ImageIcon(WeatherView.class.getResource("/weather_ico/256/"+nowWeather.getIcon() + ".png"));
                        //icon.setImage(icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                        JLabel weather_icon = new JLabel("",icon,JLabel.CENTER);
//                        weather_icon.setIcon();
//                        temp为温度
                        JLabel temp = new JLabel(nowWeather.getTemp()+"℃", JLabel.CENTER);
                        temp.setForeground(new Color(250,107,4));
                        temp.setFont(new Font("楷体",Font.BOLD,70));
//                        windDir为风向
                        JLabel windDir = new JLabel(nowWeather.getWindDir(), JLabel.CENTER);
                        windDir.setForeground(new Color(250,107,4));
                        windDir.setFont(new Font("楷体",Font.BOLD,70));
//                        windSpeed为风速
                        JLabel windSpeed = new JLabel(nowWeather.getWindSpeed()+"km/h", JLabel.CENTER);
                        windSpeed.setForeground(new Color(250,107,4));
                        windSpeed.setFont(new Font("楷体",Font.BOLD,70));
//                        humidity为湿度
                        JLabel humidity = new JLabel(nowWeather.getHumidity()+"hPa", JLabel.CENTER);
                        humidity.setForeground(new Color(250,107,4));
                        humidity.setFont(new Font("楷体",Font.BOLD,70));

//                        添加到mid_pane
                        mid_pane.add(text);
                        mid_pane.add(weather_icon);
                        mid_pane.add(temp);
                        mid_pane.add(windDir);
                        mid_pane.add(windSpeed);
                        mid_pane.add(humidity);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

    }
//    开始搜索天气数据
    public static void start_search(){
//        将mid_pane 的布局切换成网格
        mid_pane.removeAll();
        mid_pane.setLayout(new GridLayout(2,3,20,30));
//        获取输入的关键字,如果不是空的时候开始爬虫
        String text = search_textField.getText();
        if (StringUtils.isNotBlank(text)) {
            try {
//                将爬取的结果封装到集合中返回
                List<City> cities = new WeatherSpider().citySearch(text);
                if (cities == null){
//                    没有获取到时的提示信息
                    JLabel no_found_1 = new JLabel("Sorry , 没有找到您的城市!!!",JLabel.CENTER);
                    JLabel no_found_2 = new JLabel("换个地址试试吧!!!",JLabel.CENTER);
                    no_found_1.setForeground(Color.RED);
                    no_found_2.setForeground(Color.BLUE);
                    no_found_1.setFont(new Font("楷体",Font.BOLD,60));
                    no_found_2.setFont(new Font("楷体",Font.BOLD,60));
                    mid_pane.add(no_found_1);
                    mid_pane.add(no_found_2);
                    return;
                }
//                添加前,先清空 comboBox
                if (comboBox != null)
                    comboBox.removeAllItems();
//                遍历集合 , 将集合中的内容添加到comboBox
                for (final City city : cities) {
                    comboBox.addItem(city);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

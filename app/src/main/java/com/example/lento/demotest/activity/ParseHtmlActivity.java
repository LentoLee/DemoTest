package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.lento.demotest.R;
import com.example.lento.demotest.util.ThreadManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlActivity extends BaseActivity {
    private static final String TAG = "ParseHtmlActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_html);
        ThreadManager.post(ThreadManager.THREAD_BACKGROUND_NET, new Runnable() {
            @Override
            public void run() {
                testTitle();
            }
        });
    }

    private void testTitle() {
        try {
            //从一个URL加载一个Document对象。
            Document doc = Jsoup.connect("http://home.meishichina.com/show-top-type-recipe.html").get();
            //选择“美食天下”所在节点
            Elements elements = doc.select("div.top-bar");
            //打印 <a>标签里面的title
            Log.i(TAG, elements.select("a").attr("title"));


            //“椒麻鸡”和它对应的图片都在<div class="pic">中
            Elements titleAndPic = doc.select("div.pic");
            //使用Element.select(String selector)查找元素，使用Node.attr(String key)方法取得一个属性的值
            Log.i("mytag", "title:" + titleAndPic.get(1).select("a").attr("title") + "pic:" + titleAndPic.get(1).select("a").select("img").attr("data-src"));

            //所需链接在<div class="detail">中的<a>标签里面
            Elements url = doc.select("div.detail").select("a");
//            Log.i("mytag", "url:" + url.get(i).attr("href"));

            //原料在<p class="subcontent">中
            Elements burden = doc.select("p.subcontent");
            //对于一个元素中的文本，可以使用Element.text()方法
            Log.i("mytag", "burden:" + burden.get(1).text());

        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }
}

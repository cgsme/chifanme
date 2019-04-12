package com.tutu.chifanme.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.tutu.chifanme.R;
import com.tutu.chifanme.activity.BusinessDetailActivity;
import com.tutu.chifanme.activity.SearchActivity;
import com.tutu.chifanme.database.DBManager;
import com.tutu.chifanme.utils.ListViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页的fragment
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.fragment_roll_view_pager)
    RollPagerView mRollViewPager;

    @BindView(R.id.lv_business)
    ListView lv_bs;

    @OnClick(R.id.search_layout)
    public void search() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }

    private DBManager dbManager;

    // banner默认图片资源数组
    private int imgs[] = {
            R.mipmap.banner_1,
            R.mipmap.banner_5,
            R.mipmap.banner_6,
            R.mipmap.banner_2,
            R.mipmap.banner_3,
            R.mipmap.banner_4
    };

    // 店名数组
//    private String[] business_name = {"沙县小吃", "第二食堂", "第一食堂", "第三食堂"};
    private List<String> business_name = new ArrayList<String>();
    private List<Float> ratingList = new ArrayList<Float>();

    // 地址数组
    private String[] business_address = {"闽江学院北门", "闽江学院二区", "闽江学院三区", "闽江学院四区",};

    // 商店图片数组
    private int[] bs_imgs = {R.mipmap.banner_1, R.mipmap.banner_4, R.mipmap.banner_3, R.mipmap.banner_2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, homeView);
        initData();
        initView();
        return homeView;
    }

    // 初始话数据，从数据库中读取商家信息
    private void initData() {

        dbManager = new DBManager(getContext());
        Cursor cursor = dbManager.queryBusiness();
        while (cursor.moveToNext()) {
            business_name.add(cursor.getString(cursor.getColumnIndex("name")));
            ratingList.add(cursor.getFloat(cursor.getColumnIndex("rating")));
        }
    }

    private void initView() {

        // 设置播放时间间隔
        mRollViewPager.setPlayDelay(5000);  // ms
        // 设置透明度
//        mRollViewPager.setAlpha(0.5f);

        // 设置动画持续时间
        mRollViewPager.setAnimationDurtion(900);

        // 设置适配器
//        mRollViewPager.setAdapter(new loopAdapter(mRollViewPager));
        mRollViewPager.setAdapter(new LoopPagerAdapter(mRollViewPager) {

            @Override
            public View getView(ViewGroup container, int position) {

                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setImageResource(imgs[position]);
                return view;
            }

            @Override
            public int getRealCount() {

                return imgs.length;
            }
        });

        // 设置指示器（圆点）
        // 自定义指示器图片
//         mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));

        // 设置圆点指示器颜色
        mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.parseColor("#FF4081"), Color.parseColor("#F9F9F9")));

        // 设置文字指示器
        // mRollViewPager.setHintView(new TextHintView(this));

        // 隐藏指示器
        // mRollViewPager.setHintView(null);

        //=====================================================================================

       /* lv_bs.setAdapter(new SimpleCursorAdapter(getContext(), R.layout.business_lv_item, cursor,
                new String[]{"name"}, new int[]{R.id.item_tv}, 0));*/

        lv_bs.setAdapter(new BsAdapter());

        // 手动设置listview的高度，以解决在ScrollView中嵌套listview只出现一条数据的问题
        ListViewUtils.setListViewHeightBasedOnChildren(lv_bs);

        lv_bs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), BusinessDetailActivity.class);
                String bnName = business_name.get(i);
                intent.putExtra("bnName", bnName);
                intent.putExtra("bId", i+1);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


   /* public class BsCursorAdapter extends CursorAdapter{


        public BsCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            View view=inflater.inflate(R.layout.business_lv_item , viewGroup, false);

            TextView tvName = (TextView) view.findViewById(R.id.item_tv);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_iv);
            imageView.setImageResource(bs_imgs[i]);
            Log. i("cursor" ,"newView=" +view);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
    }*/

    public class BsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bs_imgs.length;
        }

        @Override
        public Object getItem(int i) {
            return bs_imgs[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = View.inflate(getContext(), R.layout.item_business_lv, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_iv);
            TextView tvName = (TextView) view.findViewById(R.id.item_tv);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_business_rating);
//            TextView tvAddress = (TextView) view.findViewById(R.id.tv_item_address);
            imageView.setImageResource(bs_imgs[i]);
            tvName.setText(business_name.get(i));
            ratingBar.setRating(ratingList.get(i));
//            tvAddress.setText(business_address[i]);
            return view;
        }
    }
}


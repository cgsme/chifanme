package com.tutu.chifanme.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.tutu.chifanme.R;
import com.tutu.chifanme.activity.CommitOrdersActivity;
import com.tutu.chifanme.adapter.GoodsAdapter;
import com.tutu.chifanme.adapter.SelectAdapter;
import com.tutu.chifanme.adapter.TypeAdapter;
import com.tutu.chifanme.beans.GoodsItem;
import com.tutu.chifanme.database.DBManager;
import com.tutu.chifanme.utils.DividerDecoration;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static android.os.Looper.getMainLooper;

/**
 * 点餐fragment
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class MakeOrderFragment extends Fragment implements View.OnClickListener {

    private int count;   // 用来记录购物车中的商品数
    private double cost;  // 记录购物车总价
    private ArrayList<Integer> countList;   //
    private ArrayList<String> costList;    // 用来保存商品单价

    private View bottomSheet;
    private ArrayList<GoodsItem> dataList,typeList;
    private SparseArray<GoodsItem> selectedList;
    private SparseIntArray groupSelect;    // 用来保存购物车中所有商品
    private RecyclerView rvSelected;

    private static DBManager dbManager;   // 数据库管理

    private ArrayList<String> selectNames;   // 用来保存所点的菜的菜名

    private GoodsAdapter myAdapter;
    private SelectAdapter selectAdapter;
    private TypeAdapter typeAdapter;

    private NumberFormat nf;   // 格式化数字（保留两位）
    private Handler mHanlder;

    @BindView(R.id.bottom)
    LinearLayout bottom;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvCost)
    TextView tvCost;

    @BindView(R.id.tvTips)
    TextView tvTips;

    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    @BindView(R.id.typeRecyclerView)
    RecyclerView  rvType;

    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.containerLayout)
    RelativeLayout anim_mask_layout;

    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;

    @BindView(R.id.itemListView)
    StickyListHeadersListView listView;

    private boolean isScroll = true;
//    private MyAdapter adapter;
    private ListView left_listView;

    private String[] leftStr = new String[] { "星期一", "星期二", "星期三", "星期四", "星期五",
            "星期六", "星期日", "星期1", "星期2", "星期3",
            "星期4", "星期5", "星期6", "星期7", "星期8",
            "星期9", "星期0", "星期01", "星期02" };

    private boolean[] flagArray = { true, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false };

    private String[][] rightStr = new String[][] {
            { "11111  早餐", "星期一  午餐", "星期一  晚餐" },
            { "星期二  早餐", "星期二  午餐", "星期二  晚餐" },
            { "星期三  早餐", "星期三  午餐", "星期三  晚餐" },
            { "星期四  早餐", "星期四  午餐", "星期四  晚餐" },
            { "星期五  早餐", "星期五  午餐", "星期五  晚餐" },
            { "星期六  早餐", "星期六  午餐", "星期六  晚餐" },
            { "星期日  早餐", "星期日  午餐", "星期日  晚餐" },

            { "星期1  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期2  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期3  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期4  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期5  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期6  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期7  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期8  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期9  早餐", "星期日  午餐", "星期日  晚餐" },
            { "星期0  早餐", "星期日  午餐", "星期日  晚餐" },

            { "星期01  早餐", "星期日  午餐", "星期日  晚餐"},
            { "星期02  早餐", "星期日  午餐", "星期日  晚餐"}
    };
    private String bName;   // 商家名称

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_make_order, container, false);
        ButterKnife.bind(this, chatView);
        dbManager = new DBManager(getContext());
        Intent intent = getActivity().getIntent();
        int id = intent.getIntExtra("bId", -1);
        bName = intent.getStringExtra("bnName");
        //=====================================================
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mHanlder = new Handler(getMainLooper());
        dataList = getGoodsList(String.valueOf(id));
        typeList = getTypeList(String.valueOf(id));
        selectedList = new SparseArray<>();
        groupSelect = new SparseIntArray();
        initView();
        bottom.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);





        //====================================================
/*
        final PinnedHeaderListView right_listview = (PinnedHeaderListView) chatView.findViewById(R.id.pinnedListView);
        final TestSectionedAdapter sectionedAdapter = new TestSectionedAdapter(getContext(), leftStr, rightStr);
        right_listview.setAdapter(sectionedAdapter);


        left_listView = (ListView) chatView.findViewById(R.id.left_listview);
        adapter = new MyAdapter();
        left_listView.setAdapter(adapter);
        left_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                isScroll = false;
                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                right_listview.setSelection(rightSection);
            }
        });

        right_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (right_listview.getLastVisiblePosition() == (right_listview.getCount() - 1)) {
                            left_listView.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (right_listview.getFirstVisiblePosition() == 0) {
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(right_listview.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == left_listView.getLastVisiblePosition()) {
                            z = z + 3;
                            left_listView.setSelection(z);
                        }
                        if (x == left_listView.getFirstVisiblePosition()) {
                            z = z - 1;
                            left_listView.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            left_listView.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });*/
        return chatView;
    }

    /**
     * 实例化控件
     */
    private void initView() {

        rvType.setLayoutManager(new LinearLayoutManager(getContext()));
        typeAdapter = new TypeAdapter(this,typeList);
        rvType.setAdapter(typeAdapter);
        rvType.addItemDecoration(new DividerDecoration(getContext()));

        myAdapter = new GoodsAdapter(dataList,this);
        listView.setAdapter(myAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                GoodsItem item = dataList.get(firstVisibleItem);
                if(typeAdapter.selectTypeId != item.typeId) {
                    typeAdapter.selectTypeId = item.typeId;
                    typeAdapter.notifyDataSetChanged();
                    rvType.smoothScrollToPosition(getSelectedGroupPosition(item.typeId));
                }
            }
        });
    }

    /**
     * 运行动画效果
     * @param start_location
     */
    public void playAnimation(int[] start_location){
        ImageView img = new ImageView(getContext());
        img.setImageResource(R.mipmap.button_add);
        setAnim(img,start_location);
    }

    /**
     * 创建动画效果
     * @param startX
     * @param startY
     * @return
     */
    private Animation createAnim(int startX, int startY){
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0]-startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1]-startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1,0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);

        return set;
    }

    /**
     * 给动画布局添加视图
     * @param vg
     * @param view
     * @param location
     */
    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {

        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y-loc[1]);
        vg.addView(view);
    }

    /**
     * 设置动画效果
     * @param v
     * @param start_location
     */
    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(anim_mask_layout, v, start_location);
        Animation set = createAnim(start_location[0],start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_mask_layout.removeView(v);
                    }
                },100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            // 点击购物车底栏
            case R.id.bottom:
                showBottomSheet();
                break;
            // 点击清空购物车
            case R.id.clear:
                clearCart();
                break;
            // 点击结算
            case R.id.tvSubmit:
                // nf.format(cost)
                GoodsItem item = null;
//                int name = selectedList.get(item.id).getCount();
                Toast.makeText(getContext(), nf.format(cost)+"", Toast.LENGTH_SHORT).show();
//                int payMoney = nf.format(cost);
                Intent intent = new Intent(getContext(), CommitOrdersActivity.class);
                intent.putStringArrayListExtra("selectList", selectNames);
                intent.putStringArrayListExtra("costList", costList);
                intent.putExtra("bName", bName);
                intent.putExtra("cost", nf.format(cost));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 添加商品
     * @param item
     * @param refreshGoodList
     */
    public void add(GoodsItem item,boolean refreshGoodList){

        int groupCount = groupSelect.get(item.typeId);
        if(groupCount==0){
            groupSelect.append(item.typeId,1);
        }else{
            groupSelect.append(item.typeId,++groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if(temp==null) {
            item.count=1;
            selectedList.append(item.id,item);
        }else {
            temp.count++;
        }
        update(refreshGoodList);
    }

    /**
     * 移除商品
     * @param item
     * @param refreshGoodList
     */
    public void remove(GoodsItem item,boolean refreshGoodList){

        int groupCount = groupSelect.get(item.typeId);
        if(groupCount==1){
            groupSelect.delete(item.typeId);
        }else if(groupCount>1){
            groupSelect.append(item.typeId,--groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if(temp!=null){
            if(temp.count<2){
                selectedList.remove(item.id);
            }else{
                item.count--;
            }
        }
        update(refreshGoodList);
    }

    /**
     * 刷新布局 总价、购买数量等
     * @param refreshGoodList
     */
    private void update(boolean refreshGoodList) {
        selectNames = new ArrayList<String>();
        costList = new ArrayList<String>();
        int size = selectedList.size();
        count = 0;
        cost = 0;
        for (int i=0;i<size;i++) {
            GoodsItem item = selectedList.valueAt(i);
           /* countList = new ArrayList<>();
            countList.add(item.count);*/
            costList.add(nf.format(item.price));
            count += item.count;
            cost += item.count*item.price;
        }

        /**
         * 遍历拿到商品名称
         */
        for (int i=0;i<size;i++) {
            GoodsItem item = selectedList.valueAt(i);
            selectNames.add(item.name);
//            Toast.makeText(getContext(),item.name+"", Toast.LENGTH_SHORT).show();
        }

        if (count <1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        // 刷新商品总件数
        tvCount.setText(String.valueOf(count));

        if (cost > 99.99) {
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }

        // 刷新商品总价
        tvCost.setText(nf.format(cost));

        if (myAdapter!=null && refreshGoodList) {
            myAdapter.notifyDataSetChanged();
        }
        if (selectAdapter!=null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (typeAdapter!=null) {
            typeAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && selectedList.size()<1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    /**
     *  清空购物车
     */
    public void clearCart(){
        selectedList.clear();
        groupSelect.clear();
        update(true);

    }

    /**
     *  根据商品id获取当前商品的采购数量
     * @param id
     * @return
     */
    public int getSelectedItemCountById(int id) {
        GoodsItem temp = selectedList.get(id);
        if(temp==null){
            return 0;
        }
        return temp.count;
    }

    /**
     * 根据类别Id获取属于当前类别的数量
     * @param typeId
     * @return
     */
    public int getSelectedGroupCountByTypeId(int typeId) {
        return groupSelect.get(typeId);
    }
    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId){
        for(int i=0;i<typeList.size();i++){
            if(typeId==typeList.get(i).typeId){
                return i;
            }
        }
        return 0;
    }

    /**
     * 当点击类别时
     * @param typeId
     */
    public void onTypeClicked(int typeId) {
        listView.setSelection(getSelectedPosition(typeId));
    }

    /**
     * 获取选择的位置
     * @param typeId
     * @return
     */
    private int getSelectedPosition(int typeId) {
        int position = 0;
        for(int i=0;i<dataList.size();i++){
            if(dataList.get(i).typeId == typeId){
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 创建购物车
     * @return
     */
    private View createBottomSheetView()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getActivity().getWindow().getDecorView(),false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        selectAdapter = new SelectAdapter(this,selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    /**
     * 显示购物车
     */
    private void showBottomSheet(){
        if(bottomSheet==null){
            bottomSheet = createBottomSheetView();
        }
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(selectedList.size()!=0){
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    /**
     * 适配器
     */
    /*public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return leftStr.length;
        }

        @Override
        public Object getItem(int arg0) {
            return leftStr[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            Holder holder = null;
            if (arg1 == null) {
                holder = new Holder();
                arg1 = LayoutInflater.from(getContext()).inflate(R.layout.left_list_item, null);
                holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
                arg1.setTag(holder);
            } else {
                holder = (Holder) arg1.getTag();
            }
            holder.updataView(arg0);
            return arg1;
        }

        private class Holder {

            private TextView left_list_item;

            public void updataView(final int position) {
                left_list_item.setText(leftStr[position]);
                if (flagArray[position]) {
                    left_list_item.setBackgroundColor(Color.LTGRAY);
                } else {
                    left_list_item.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    /**
     * 查询菜单数据（初始化菜单）
     * @return
     */
    public Cursor initData(String id) {
        return dbManager.queryMenus(id);
    }


    //=========================================================================================


    private  ArrayList<GoodsItem> goodsList;
    private  ArrayList<GoodsItem> typesList;

    /**
     * 初始化商品列表数据
     */
    private void initGoods(String businessId) {

        goodsList = new ArrayList<>();
        typesList = new ArrayList<>();
        GoodsItem item = null;

        Cursor menusCursor = initData(businessId);
        int i =0;
        while (menusCursor.moveToNext()) {
            int id = menusCursor.getInt(menusCursor.getColumnIndex("_id"));
            double price = menusCursor.getDouble(menusCursor.getColumnIndex("price"));
            String name = menusCursor.getString(menusCursor.getColumnIndex("menu_name"));
            int typeId = menusCursor.getInt(menusCursor.getColumnIndex("type_id"));
            String typeName = menusCursor.getString(menusCursor.getColumnIndex("type_name"));
            int bId = menusCursor.getInt(menusCursor.getColumnIndex("business_id"));
            item = new GoodsItem(id, price, name, typeId, typeName, bId);
            goodsList.add(item);
            while (i >= 7) {
                typesList.add(item);
                i=-1;
            }
            i++;
        }
    }

    /**
     * 根据商家id获取商品列表
     * @return
     */
    public ArrayList<GoodsItem> getGoodsList(String id1) {
        if(goodsList==null){
            initGoods(id1);
        }
        return goodsList;
    }

    /**
     * 根据商家id获取类别列表
     * @return
     */
    public ArrayList<GoodsItem> getTypeList(String id2) {
        if(typesList==null) {
            initGoods(id2);
        }
        return typesList;
    }
}

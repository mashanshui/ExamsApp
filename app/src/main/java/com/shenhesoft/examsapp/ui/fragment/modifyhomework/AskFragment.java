package com.shenhesoft.examsapp.ui.fragment.modifyhomework;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MsgAdapter;
import com.shenhesoft.examsapp.network.model.ChatMsgModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.AskPresent;
import com.shenhesoft.examsapp.util.event.ChatEvent;
import com.shenhesoft.examsapp.view.AskView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc 师生问答
 */
public class AskFragment extends XLazyFragment<AskPresent> implements AskView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.et_imput_box)
    EditText etImputBox;
    @BindView(R.id.btn_send)
    QMUIRoundButton btnSend;
    @BindView(R.id.rv_chat_message)
    RecyclerView rvChatMessage;

    private String mParam1;
    private String mParam2;

    private int start = 0;
    private int length = 100;
    private ProductModel productModel;
    private String chatId;

    private MsgAdapter msgAdapter;
    private List<ChatMsgModel> msgBeans;

    public AskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskFragment.
     */
    public static AskFragment newInstance(String param1, String param2) {
        AskFragment fragment = new AskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        btnSend.setChangeAlphaWhenPress(true);
        msgBeans = new ArrayList<>();
        msgAdapter = new MsgAdapter(msgBeans);
        rvChatMessage.setLayoutManager(new LinearLayoutManager(context));
        rvChatMessage.setAdapter(msgAdapter);
        etImputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btnSend.setClickable(true);
                    QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) btnSend.getBackground();
                    buttonDrawable.setBgData(ContextCompat.getColorStateList(context, R.color.colorPrimary));
                } else {
                    btnSend.setClickable(false);
                    QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) btnSend.getBackground();
                    buttonDrawable.setBgData(ContextCompat.getColorStateList(context, R.color.qmui_config_color_gray_9));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ask;
    }

    @Override
    public AskPresent newP() {
        return new AskPresent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            getActivity().finish();
        }
        productModel = listBean;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyChatEvent(ChatEvent chatEvent) {
        chatId = chatEvent.getChatId();
        if (TextUtils.isEmpty(chatId)) {
            IToast.showShort("请先上传作文");
            return;
        }
        getP().loadData(chatId);
    }



    @Override
    public boolean useEventBus() {
        return true;
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        if (TextUtils.isEmpty(chatId)) {
            IToast.showShort("请先上传作文");
            return;
        }
        getP().sendMessage(chatId);
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void updateData(List<ChatMsgModel> msgModels) {
        if (!msgBeans.isEmpty()) {
            msgBeans.clear();
        }
        msgBeans.addAll(msgModels);
        refreshData();
    }

    @Override
    public String getSendMessage() {
        return etImputBox.getText().toString();
    }

    @Override
    public void clearSendMessage() {
        etImputBox.setText("");
    }

    private void refreshData() {
        msgAdapter.notifyDataSetChanged();
        if (msgAdapter.getItemCount() > 0) {
            rvChatMessage.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
        }
    }
}

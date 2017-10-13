package com.sandboxol.blockymods.view.main;

import android.databinding.ObservableField;

import com.sandboxol.blockymods.R;
import com.sandboxol.blockymods.view.main.chat.ChatFragment;
import com.sandboxol.blockymods.view.main.game.GameFragment;
import com.sandboxol.blockymods.view.main.index.IndexFragment;
import com.sandboxol.blockymods.view.main.me.MeFragment;
import com.sandboxol.common.base.app.BaseFragment;
import com.sandboxol.common.binding.adapter.RadioGroupBindingAdapters;
import com.sandboxol.common.binding.viewmodel.ViewModel;
import com.sandboxol.common.command.ReplyCommand;

/**
 * Created by Jimmy on 2017/10/13 0013.
 */
public class MainViewModel implements ViewModel {

    private MainActivity activity;

    public MainViewModel(MainActivity activity) {
        this.activity = activity;
        onCheck(R.id.rb_1);
    }

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<Boolean> isShowLeftButton = new ObservableField<>();
    public ObservableField<Boolean> isShowRightButton = new ObservableField<>();

    public ReplyCommand<RadioGroupBindingAdapters.CheckedDataWrapper> onCheckCommand = new ReplyCommand<>(checkedDataWrapper -> onCheck(checkedDataWrapper.getCheckedId()));

    private void onCheck(int checkedId) {
        switch (checkedId) {
            case R.id.rb_1:
                title.set("INDEX");
                isShowLeftButton.set(true);
                isShowRightButton.set(true);
                replaceFragment(new IndexFragment());
                break;
            case R.id.rb_2:
                title.set("GAME");
                isShowLeftButton.set(false);
                isShowRightButton.set(true);
                replaceFragment(new GameFragment());
                break;
            case R.id.rb_3:
                title.set("CHAT");
                isShowLeftButton.set(true);
                isShowRightButton.set(false);
                replaceFragment(new ChatFragment());
                break;
            case R.id.rb_4:
                title.set("ME");
                isShowLeftButton.set(false);
                isShowRightButton.set(false);
                replaceFragment(new MeFragment());
                break;
        }
    }

    private void replaceFragment(BaseFragment fragment) {
        activity.replaceFragment(fragment);
    }

}

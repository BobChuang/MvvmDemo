package com.bob.ezil.view.activity;

import androidx.databinding.ObservableField;

import com.bob.common.base.app.TemplateFragment;
import com.bob.common.base.viewmodel.ViewModel;
import com.bob.common.binding.adapter.RadioGroupBindingAdapters;
import com.bob.common.command.ReplyCommand;
import com.bob.common.messenger.Messenger;
import com.bob.ezil.R;
import com.bob.ezil.config.MessageToken;
import com.bob.ezil.view.fragment.chat.ChatFragment;
import com.bob.ezil.view.fragment.game.GameFragment;
import com.bob.ezil.view.fragment.home.HomeFragment;
import com.bob.ezil.view.fragment.me.MeFragment;

/**
 * Created by Bob on 2021/06/30.
 */
public class MainViewModel extends ViewModel {

    private MainActivity activity;
    private HomeFragment homeFragment = new HomeFragment();
    public ReplyCommand onRightClick = new ReplyCommand(this::onRefresh);

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
                title.set("Ezil");
                isShowLeftButton.set(false);
                isShowRightButton.set(false);
                replaceFragment(homeFragment);
                break;
            case R.id.rb_2:
                title.set("GAME");
                isShowLeftButton.set(false);
                isShowRightButton.set(false);
                replaceFragment(new GameFragment());
                break;
            case R.id.rb_3:
                title.set("CHAT");
                isShowLeftButton.set(false);
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

    private void replaceFragment(TemplateFragment fragment) {
        activity.replaceFragment(fragment);
    }

    private void onRefresh() {
        Messenger.getDefault().sendNoMsg(MessageToken.REFRESH_MAIN);
    }

}

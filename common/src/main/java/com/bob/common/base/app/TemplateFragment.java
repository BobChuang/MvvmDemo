package com.bob.common.base.app;

import androidx.databinding.ViewDataBinding;
import android.view.View;
import android.widget.TextView;

import com.bob.common.R;
import com.bob.common.base.viewmodel.ViewModel;

/**
 * Created by Jimmy on 2016/10/19 0019.
 */
public abstract class TemplateFragment<VM extends ViewModel, D extends ViewDataBinding> extends BaseFragment<VM, D> {

    public void onLeftButtonClick(View v) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void onRightButtonClick(View v) {

    }

    public void changeActionBarTitle(String title) {
        if (activity != null && activity instanceof TemplateActivity) {
            TextView tvTemplateTitle = activity.findViewById(R.id.tvTemplateTitle);
            tvTemplateTitle.setText(title);
        }
    }

    public void hideLeftButton() {
        if (activity != null && activity instanceof TemplateActivity) {
            activity.findViewById(R.id.ibTemplateLeft).setVisibility(View.GONE);
        }
    }
    public void onBackPressed(){

    }
}

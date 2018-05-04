package com.marcosilv7.narutodelivery.ui.base;

import android.view.View;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;

import me.yokeyword.fragmentation.SupportActivity;

public class CustomSupportActivity  extends SupportActivity{
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
